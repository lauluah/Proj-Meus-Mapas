const params = new URLSearchParams(window.location.search);
const mapaId = params.get("id");

if (!mapaId) {
    alert("Mapa inválido");
    window.location.href = "index.html";
}

function voltar() {
    window.location.href = "index.html";
}

const map = L.map('map').setView([-23.55, -46.63], 13);

L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '© OpenStreetMap'
}).addTo(map);

let marcador = null;

map.on('click', e => {
    if (marcador) map.removeLayer(marcador);
    marcador = L.marker(e.latlng).addTo(map);
});

function salvarPonto() {
    const msg = document.getElementById("mensagem");
    msg.innerText = "";
    msg.className = "";

    if (!marcador) {
        msg.innerText = "Clique no mapa para marcar o ponto.";
        msg.classList.add("erro");
        return;
    }

    const ponto = {
        nome: document.getElementById("nomePonto").value,
        descricao: document.getElementById("descricaoPonto").value,
        latitude: marcador.getLatLng().lat,
        longitude: marcador.getLatLng().lng
    };

    fetch(`http://localhost:8080/mapas/${mapaId}/pontos`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(ponto)
    })
    .then(res => {
        if (!res.ok) throw new Error();
        msg.innerText = "Ponto adicionado com sucesso!";
        msg.classList.add("sucesso");

        document.getElementById("nomePonto").value = "";
        document.getElementById("descricaoPonto").value = "";
        map.removeLayer(marcador);
        marcador = null;
    })
    .catch(() => {
        msg.innerText = "Erro ao adicionar ponto";
        msg.classList.add("erro");
    });
}
