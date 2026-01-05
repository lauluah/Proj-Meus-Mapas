# 🗺️ MeusMapas

<div align="center">

![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Leaflet](https://img.shields.io/badge/Leaflet-1.9.4-199900?style=for-the-badge&logo=leaflet&logoColor=white)

**Sistema interativo para criar e gerenciar mapas com pontos de interesse.**

[Funcionalidades](#-funcionalidades) •
[Tecnologias](#-tecnologias) •
[Instalação](#-instalação) •
[API](#-api) 

</div>

---

## ✨ Funcionalidades

- 🗺️ **Criar mapas** e **criar pontos**
- 📍 **Adicionar pontos** clicando diretamente no mapa
- ✏️ **Editar e excluir** pontos e mapas existentes
- 🔍 **Visualizar coordenadas** (latitude/longitude) automaticamente
- 💾 **Persistência de dados** com banco de dados (supabase)

---

## 🛠️ Tecnologias

### Backend
| Tecnologia | Descrição |
|------------|-----------|
| **Java 21** | Linguagem de programação |
| **Spring Boot 3.2.x** | Framework backend |
| **Spring Data JPA** | Persistência de dados |
| **Bean Validation** | Validação de dados |
| **Maven** | Gerenciador de dependências |

### Frontend
| Tecnologia | Descrição |
|------------|-----------|
| **HTML5** | Estrutura da página |
| **CSS3** | Estilização moderna |
| **JavaScript** | Interatividade |
| **Leaflet.js** | Biblioteca de mapas |
| **OpenStreetMap** | Tiles do mapa |

---

## 🚀 Instalação

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6+

### Passo a passo

```bash
# Clone o repositório
git clone https://github.com/lauluah/Proj-Meus-Mapas.git

# Entre na pasta do projeto
cd meusmapass

# Execute o backend
./mvnw spring-boot:run

Frontend

Após rodar o backend, abra o arquivo index.html e clique em um dos ícones dos navegadores à direita para abrir o frontend diretamente no navegador.

O frontend é servido pelo **servidor HTTP do IntelliJ**, acessível em:

http://localhost:63342/meusmapass/templates/index.html

⚠️ Importante:
- A porta **63342** é utilizada pelo frontend.
- A porta **8080** é utilizada apenas pelo backend (Spring Boot).
- O backend deve estar em execução antes de acessar o frontend, para que as requisições funcionem corretamente.
