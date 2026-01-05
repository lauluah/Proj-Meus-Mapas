document.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);
    const mapaId = params.get("id");

    if (!mapaId) {
        alert("Mapa inválido");
        window.location.href = "/index.html";
        return;
    }

    const inputNome = document.getElementById("pontoNome");
    const inputDescricao = document.getElementById("pontoDescricao");
    const inputLat = document.getElementById("pontoLat");
    const inputLng = document.getElementById("pontoLng");
    const formSection = document.getElementById("formSection");
    const mensagem = document.getElementById("mensagem");
    const instructions = document.getElementById("instructions");
    const pontosLista = document.getElementById("pontosLista");
    const pontosCount = document.getElementById("pontosCount");
    const emptyState = document.getElementById("emptyState");

    let pontos = [];
    let markersSalvos = {};

    const map = L.map("map").setView([-23.5505, -46.6333], 13);

    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
        attribution: "© OpenStreetMap"
    }).addTo(map);

    let marcador = null;

    map.on("click", (e) => {
        if (marcador) map.removeLayer(marcador);

        marcador = L.marker(e.latlng).addTo(map);

        inputLat.value = e.latlng.lat.toFixed(6);
        inputLng.value = e.latlng.lng.toFixed(6);

        formSection.classList.add("active");
        if (instructions) instructions.style.display = "none";
    });

   function salvarPonto() {
       mensagem.innerText = "";
       mensagem.className = "";

       if (!marcador) {
           mensagem.innerText = "Clique no mapa para marcar o ponto.";
           mensagem.className = "erro";
           return;
       }

       const nome = inputNome.value.trim();
       const descricao = inputDescricao.value.trim();

       if (!nome) {
           mensagem.innerText = "Informe o nome do ponto.";
           mensagem.className = "erro";
           return;
       }

         if (nome.length < 3 || nome.length > 20) {
               mensagem.innerText = "O nome deve ter entre 3 e 20 caracteres.";
               mensagem.className = "erro";
               return;
           }

       const ponto = {
           nome,
           descricao,
           latitude: marcador.getLatLng().lat,
           longitude: marcador.getLatLng().lng
       };

       fetch(`http://localhost:8080/mapas/${mapaId}/pontos`, {
           method: "POST",
           headers: { "Content-Type": "application/json" },
           body: JSON.stringify(ponto)
       })

       .then(async res => {
           if (!res.ok) {
               const erro = await res.text();
               console.error("Erro backend:", erro);
               throw new Error(erro);
           }
           return res.json();
       })

       .then(() => {
           mensagem.innerText = "Ponto adicionado com sucesso!";
           mensagem.className = "sucesso";
           carregarPontos();
           limparFormulario();
       })

       .catch(err => {
           mensagem.innerText = "Erro ao salvar ponto. Veja o console.";
           mensagem.className = "erro";
       });
   }

    function adicionarMarkerSalvo(ponto) {
    const marker = L.marker([ponto.latitude, ponto.longitude])
        .addTo(map)
        .bindPopup(`
            <strong>${escapeHtml(ponto.nome)}</strong><br>
            ${ponto.descricao ?? ""}
        `);

    markersSalvos[ponto.id] = marker;
}

   function carregarPontos() {
       fetch(`http://localhost:8080/mapas/${mapaId}/pontos`)
           .then(res => {
               if (!res.ok) throw new Error();
               return res.json();
           })
           .then(data => {
               pontos = data;
               renderizarPontos();
           })
           .catch(() => {
               console.error("Erro ao carregar pontos");
           });
   }

   function limparMarkersSalvos() {
       Object.values(markersSalvos).forEach(marker => {
           map.removeLayer(marker);
       });
       markersSalvos = {};
   }

    function renderizarPontos() {
           pontosCount.innerText = pontos.length;
           limparMarkersSalvos();

           if (pontos.length === 0) {
               emptyState.style.display = "block";
               pontosLista.innerHTML = "";
               return;
           }

           emptyState.style.display = "none";

               pontos.forEach(ponto => {
                    adicionarMarkerSalvo(ponto);
                });

           pontosLista.innerHTML = pontos.map((ponto, index) => `
               <div class="point-item">
                   <div class="point-marker">${index + 1}</div>
                   <div class="point-info">
                       <div class="point-name">${escapeHtml(ponto.nome)}</div>
                       <div class="point-coords">
                           ${ponto.latitude.toFixed(4)}, ${ponto.longitude.toFixed(4)}
                       </div>
                   </div>
                   <div class="point-actions">
                       <button
                         class="point-action-btn edit"
                         onclick="abrirModalEdicao(${ponto.id})"
                         title="Editar">
                           <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                               <path d="M17 3a2.85 2.83 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5Z"></path>
                               <path d="m15 5 4 4"></path>
                           </svg>
                       </button>
                      <button
                        class="point-action-btn delete"
                        onclick="abrirModalExclusao(${ponto.id})"
                        title="Excluir">
                           <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                               <path d="M3 6h18"></path>
                               <path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6"></path>
                               <path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2"></path>
                           </svg>
                       </button>
                   </div>
               </div>
           `).join("");
     }


    function escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML.replace(/'/g, "\\'").replace(/"/g, '\\"');
    }

    function limparFormulario() {
        if (marcador) {
            map.removeLayer(marcador);
            marcador = null;
        }

        inputNome.value = "";
        inputDescricao.value = "";
        inputLat.value = "";
        inputLng.value = "";
        formSection.classList.remove("active");
    }

  function abrirModalEdicao(id) {
      const ponto = pontos.find(p => p.id === id);
      if (!ponto) return;

      editPontoId.value = ponto.id;
      editNome.value = ponto.nome;
      editDescricao.value = ponto.descricao || "";

      modalMensagem.innerText = "";
      modalMensagem.className = "";
      modalOverlay.classList.add("active");
  }

    function fecharModal() {
        modalOverlay.classList.remove("active");
        editPontoId.value = "";
        editNome.value = "";
        editDescricao.value = "";
    }

    function salvarEdicao() {
        const id = editPontoId.value;
        const novoNome = editNome.value.trim();
        const novaDescricao = editDescricao.value.trim();

        if (!novoNome) {
            modalMensagem.innerText = "O nome é obrigatório.";
            modalMensagem.className = "erro";
            return;
        }

         if (novoNome.length < 3 || novoNome.length > 20) {
                modalMensagem.innerText = "O nome deve ter entre 3 e 20 caracteres.";
                modalMensagem.className = "erro";
                return;
            }

        fetch(`http://localhost:8080/mapas/${mapaId}/pontos/${id}?novoNome=${encodeURIComponent(novoNome)}&novaDescricao=${encodeURIComponent(novaDescricao)}`, {
            method: "PUT"
        })
        .then(async res => {
            if (!res.ok) {
                const erro = await res.text();
                throw new Error(erro);
            }
            return res.json();
        })
        .then(() => {
            fecharModal();
            carregarPontos();
            mensagem.innerText = "Ponto atualizado com sucesso!";
            mensagem.className = "sucesso";
        })
        .catch(err => {
            modalMensagem.innerText = "Erro ao atualizar ponto.";
            modalMensagem.className = "erro";
            console.error(err);
        });
    }

   function abrirModalExclusao(id) {
       const ponto = pontos.find(p => p.id === id);
       if (!ponto) return;

       deletePontoId.value = ponto.id;
       deletePontoNome.innerText = ponto.nome;
       deleteModalOverlay.classList.add("active");
   }

    function fecharDeleteModal() {
        deleteModalOverlay.classList.remove("active");
        deletePontoId.value = "";
    }

    function confirmarExclusao() {
        const id = deletePontoId.value;

        fetch(`http://localhost:8080/mapas/${mapaId}/pontos/${id}`, {
            method: "DELETE"
        })
        .then(res => {
            if (!res.ok) throw new Error();
            fecharDeleteModal();
            carregarPontos();
            mensagem.innerText = "Ponto excluído com sucesso!";
            mensagem.className = "sucesso";
        })
        .catch(err => {
            fecharDeleteModal();
            mensagem.innerText = "Erro ao excluir ponto.";
            mensagem.className = "erro";
        });
    }

    carregarPontos();

    window.salvarPonto = salvarPonto;
    window.cancelarPonto = limparFormulario;
    window.fecharInstrucoes = () => instructions.style.display = "none";
    window.toggleSidebar = () => {
        document.querySelector(".sidebar")?.classList.toggle("expanded");
    };
    window.abrirModalEdicao = abrirModalEdicao;
    window.fecharModal = fecharModal;
    window.salvarEdicao = salvarEdicao;
    window.abrirModalExclusao = abrirModalExclusao;
    window.fecharDeleteModal = fecharDeleteModal;
    window.confirmarExclusao = confirmarExclusao;
});
