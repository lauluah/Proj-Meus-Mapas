const API_URL = "http://localhost:8080/mapas";

const ToastIcons = {
    error: `<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
        <circle cx="12" cy="12" r="10"></circle>
        <line x1="15" y1="9" x2="9" y2="15"></line>
        <line x1="9" y1="9" x2="15" y2="15"></line>
    </svg>`,
    success: `<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
        <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
        <polyline points="22 4 12 14.01 9 11.01"></polyline>
    </svg>`,
    warning: `<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
        <path d="m21.73 18-8-14a2 2 0 0 0-3.48 0l-8 14A2 2 0 0 0 4 21h16a2 2 0 0 0 1.73-3Z"></path>
        <line x1="12" y1="9" x2="12" y2="13"></line>
        <line x1="12" y1="17" x2="12.01" y2="17"></line>
    </svg>`,
    info: `<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
        <circle cx="12" cy="12" r="10"></circle>
        <line x1="12" y1="16" x2="12" y2="12"></line>
        <line x1="12" y1="8" x2="12.01" y2="8"></line>
    </svg>`
};

const ToastTitles = {
    error: 'Erro',
    success: 'Sucesso',
    warning: 'Atenção',
    info: 'Informação'
};

function showToast(message, type = 'info', duration = 4000, title = null) {
    const container = document.getElementById('toastContainer');

    const toast = document.createElement('div');
    toast.className = `toast toast-${type}`;

    const displayTitle = title || ToastTitles[type];

    toast.innerHTML = `
        <div class="toast-icon">
            ${ToastIcons[type]}
        </div>
        <div class="toast-content">
            <div class="toast-title">${displayTitle}</div>
            <div class="toast-message">${message}</div>
        </div>
        <button class="toast-close" onclick="closeToast(this)">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <line x1="18" y1="6" x2="6" y2="18"></line>
                <line x1="6" y1="6" x2="18" y2="18"></line>
            </svg>
        </button>
        <div class="toast-progress" style="animation-duration: ${duration}ms"></div>
    `;

    container.appendChild(toast);
    const timeout = setTimeout(() => {
        removeToast(toast);
    }, duration);

    toast.dataset.timeout = timeout;

    return toast;
}

function closeToast(button) {
    const toast = button.closest('.toast');
    if (toast.dataset.timeout) {
        clearTimeout(parseInt(toast.dataset.timeout));
    }
    removeToast(toast);
}

function removeToast(toast) {
    if (!toast || toast.classList.contains('hiding')) return;

    toast.classList.add('hiding');

    setTimeout(() => {
        if (toast.parentNode) {
            toast.parentNode.removeChild(toast);
        }
    }, 300);
}

function toastError(message, title = null) {
    return showToast(message, 'error', 5000, title);
}

function toastSuccess(message, title = null) {
    return showToast(message, 'success', 4000, title);
}

function toastWarning(message, title = null) {
    return showToast(message, 'warning', 4500, title);
}

function toastInfo(message, title = null) {
    return showToast(message, 'info', 4000, title);
}

function validarNomeMapa(nome, campo = 'mapa') {
    nome = nome.trim();

    if (!nome) {
        toastError(`Por favor, informe o nome do ${campo}.`, 'Campo obrigatório');
        return { valid: false, nome };
    }

    if (nome.length < 3) {
        toastError(`O nome do ${campo} deve ter pelo menos 3 caracteres.`, 'Nome muito curto');
        return { valid: false, nome };
    }

    if (nome.length > 20) {
        toastError(`O nome do ${campo} deve ter no máximo 20 caracteres.`, 'Nome muito longo');
        return { valid: false, nome };
    }

    const caracteresInvalidos = /[<>{}[\]\\\/]/;
    if (caracteresInvalidos.test(nome)) {
        toastError('O nome contém caracteres inválidos. Evite usar: < > { } [ ] \\ /', 'Caracteres inválidos');
        return { valid: false, nome };
    }

    return { valid: true, nome };
}

function marcarCampoErro(inputId, isError = true) {
    const input = document.getElementById(inputId);
    if (input) {
        if (isError) {
            input.classList.add('input-error');
        } else {
            input.classList.remove('input-error');
        }
    }
}

async function criarMapa() {
    const input = document.getElementById("mapName");
    const nomeOriginal = input.value;

    marcarCampoErro('mapName', false);

    const validacao = validarNomeMapa(nomeOriginal);

    if (!validacao.valid) {
        marcarCampoErro('mapName', true);
        input.focus();
        return;
    }

    const nome = validacao.nome;

    try {
        const res = await fetch(API_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ nome })
        });

        if (!res.ok) {
            const errorData = await res.json().catch(() => ({}));

            if (res.status === 409 || errorData.message?.includes('já existe')) {
                toastWarning('Já existe um mapa com este nome. Escolha outro nome.', 'Nome duplicado');
                marcarCampoErro('mapName', true);
                return;
            }

            throw new Error(errorData.message || "Erro ao criar mapa");
        }

        toastSuccess(`Mapa "${nome}" criado com sucesso!`);
        input.value = "";
        carregarMapas();

    } catch (err) {
        console.error(err);
        toastError('Não foi possível criar o mapa. Verifique sua conexão e tente novamente.', 'Erro de conexão');
    }
}

async function carregarMapas() {
    try {
        const res = await fetch(API_URL);

        if (!res.ok) throw new Error("Erro ao buscar mapas");

        const mapas = await res.json();
        renderizarMapas(mapas);
    } catch (err) {
        console.error(err);
        toastError('Não foi possível carregar os mapas. Verifique sua conexão.', 'Erro ao carregar');
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
                <div class="map-icon">📍</div>
                <div class="map-details">
                    <h3>${escapeHtml(mapa.nome)}</h3>
                    <p>Criado em ${formatarDataHora(mapa.dataCriacao)}</p>
                </div>
            </div>

            <div class="map-actions">
                <button class="btn-edit"
                    onclick="event.stopPropagation(); abrirModalEditarMapa(${mapa.id}, '${escapeHtml(mapa.nome)}')"
                    title="Editar mapa">
                    ✏️
                </button>

                <button class="btn-delete"
                    onclick="event.stopPropagation(); abrirModalExcluirMapa(${mapa.id}, '${escapeHtml(mapa.nome)}')"
                    title="Excluir mapa">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M3 6h18"></path>
                        <path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6"></path>
                        <path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2"></path>
                    </svg>
                </button>
            </div>
        </li>
    `).join('');
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

function abrirModalExcluirMapa(id, nome) {
    document.getElementById("deleteMapaId").value = id;
    document.getElementById("deleteMapaNome").innerText = nome;
    document.getElementById("deleteMapaModal").classList.add("active");
}

function fecharDeleteMapaModal() {
    document.getElementById("deleteMapaModal").classList.remove("active");
    document.getElementById("deleteMapaId").value = "";
}

async function confirmarExclusaoMapa() {
    const id = document.getElementById("deleteMapaId").value;
    const nome = document.getElementById("deleteMapaNome").innerText;

    try {
        const res = await fetch(`${API_URL}/${id}`, {
            method: "DELETE"
        });

        if (!res.ok) throw new Error();

        fecharDeleteMapaModal();
        toastSuccess(`Mapa "${nome}" excluído com sucesso.`);
        carregarMapas();

    } catch (err) {
        console.error(err);
        toastError('Não foi possível excluir o mapa. Tente novamente.', 'Erro ao excluir');
    }
}

function abrirModalEditarMapa(id, nome) {
    document.getElementById("editMapaId").value = id;
    document.getElementById("editMapaNome").value = nome;
    marcarCampoErro('editMapaNome', false);
    document.getElementById("editMapaModal").classList.add("active");

    setTimeout(() => {
        document.getElementById("editMapaNome").focus();
    }, 100);
}

function fecharEditarMapaModal() {
    document.getElementById("editMapaModal").classList.remove("active");
    document.getElementById("editMapaId").value = "";
    marcarCampoErro('editMapaNome', false);
}

async function confirmarEdicaoMapa() {
    const id = document.getElementById("editMapaId").value;
    const nomeOriginal = document.getElementById("editMapaNome").value;

    marcarCampoErro('editMapaNome', false);

    const validacao = validarNomeMapa(nomeOriginal);

    if (!validacao.valid) {
        marcarCampoErro('editMapaNome', true);
        document.getElementById("editMapaNome").focus();
        return;
    }

    const nome = validacao.nome;

    try {
        const res = await fetch(`${API_URL}/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ nome })
        });

        if (!res.ok) {
            const errorData = await res.json().catch(() => ({}));

            if (res.status === 409 || errorData.message?.includes('já existe')) {
                toastWarning('Já existe um mapa com este nome. Escolha outro nome.', 'Nome duplicado');
                marcarCampoErro('editMapaNome', true);
                return;
            }

            throw new Error();
        }

        fecharEditarMapaModal();
        toastSuccess(`Mapa renomeado para "${nome}".`);
        carregarMapas();

    } catch (err) {
        console.error(err);
        toastError('Não foi possível editar o mapa. Tente novamente.', 'Erro ao editar');
    }
}

function formatarDataHora(dataISO) {
    if (!dataISO) return "—";

    const data = new Date(dataISO);
    if (isNaN(data)) return "—";

    return data.toLocaleString("pt-BR", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric",
        hour: "2-digit",
        minute: "2-digit"
    });
}

function abrirMapa(id) {
    window.location.href = `../templates/mapa.html?id=${id}`;
}

document.getElementById('mapName').addEventListener('keypress', e => {
    if (e.key === 'Enter') criarMapa();
});

document.getElementById('mapName').addEventListener('input', () => {
    marcarCampoErro('mapName', false);
});

document.getElementById('editMapaNome')?.addEventListener('keypress', e => {
    if (e.key === 'Enter') confirmarEdicaoMapa();
});

document.getElementById('editMapaNome')?.addEventListener('input', () => {
    marcarCampoErro('editMapaNome', false);
});

document.querySelectorAll('.modal-overlay').forEach(overlay => {
    overlay.addEventListener('click', (e) => {
        if (e.target === overlay) {
            overlay.classList.remove('active');
        }
    });
});

document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') {
        document.querySelectorAll('.modal-overlay.active').forEach(modal => {
            modal.classList.remove('active');
        });
    }
});

document.addEventListener('DOMContentLoaded', carregarMapas);
