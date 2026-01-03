const API_URL = "http://localhost:8080/mapas";

    async function criarMapa() {
        const input = document.getElementById('mapName');
        const nome = input.value.trim();

        if (!nome) {
            input.style.borderColor = '#dc2626';
            input.style.boxShadow = '0 0 0 3px rgba(220, 38, 38, 0.15)';
            setTimeout(() => {
                input.style.borderColor = '';
                input.style.boxShadow = '';
            }, 2000);
            return;
        }

        try {
            const res = await fetch("http://localhost:8080/mapas", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ nome })
            });

            if (!res.ok) throw new Error("Erro ao criar mapa");

            input.value = '';
            carregarMapas();
        } catch (err) {
            console.error(err);
        }
    }

    async function deletarMapa(id) {
        try {
            const res = await fetch(`http://localhost:8080/mapas/${id}`, {
                method: "DELETE"
            });

            if (!res.ok) throw new Error("Erro ao deletar mapa");

            carregarMapas();
        } catch (err) {
            console.error(err);
        }
    }

    async function carregarMapas() {
        try {
            const res = await fetch("http://localhost:8080/mapas");

            if (!res.ok) throw new Error("Erro ao buscar mapas");

            const mapas = await res.json();
            renderizarMapas(mapas);
        } catch (err) {
            console.error(err);
        }
    }

    function renderizarMapas(mapas) {
        const lista = document.getElementById('mapList');
        const emptyState = document.getElementById('emptyState');
        const countBadge = document.getElementById('countBadge');

        countBadge.textContent = `${mapas.length} ${mapas.length === 1 ? 'mapa' : 'mapas'}`;

        if (mapas.length === 0) {
            lista.innerHTML = '';
            lista.appendChild(emptyState);
            emptyState.style.display = 'block';
            return;
        }

        emptyState.style.display = 'none';

        lista.innerHTML = mapas.map((mapa, index) => `
           <li class="map-item"
               onclick="abrirMapa(${mapa.id})"
               style="animation-delay: ${index * 100}ms">

                <div class="map-info">
                    <div class="map-icon">...</div>
                    <div class="map-details">
                        <h3>${mapa.nome}</h3>
                        <p>Criado em ${mapa.dataCriacao ?? ''}</p>
                    </div>
                </div>

                <button class="btn-delete"
                    onclick="event.stopPropagation(); deletarMapa(${mapa.id})">
                    🗑
                </button>
            </li>
        `).join('');
    }

    function abrirMapa(id) {
        window.location.href = `../templates/mapa.html?id=${id}`;
    }

    document.getElementById('mapName').addEventListener('keypress', e => {
        if (e.key === 'Enter') criarMapa();
    });

    document.addEventListener('DOMContentLoaded', carregarMapas);
