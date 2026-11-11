# Sistema de Avaliações — API

API Spring Boot protegida por Auth0. `GET /avaliacoes` requer papel `USER` ou `ADMIN`; `POST /avaliacoes` requer `ADMIN`.

## Auth0

Crie uma API no Auth0 e configure `AUTH0_ISSUER_URI` e `AUTH0_AUDIENCE`. Inclua no access token o claim `https://avaliacoes.insper.br/roles` com `USER` e/ou `ADMIN` (ou permissões com os mesmos nomes).

## Execução local

Copie `.env.example` para `.env`, preencha as variáveis e execute `docker compose up --build`.

## Deploy

O workflow em `.github/workflows/deploy.yml` exige os secrets `EC2_HOST`, `EC2_USER`, `EC2_SSH_KEY`, `POSTGRES_PASSWORD`, `AUTH0_ISSUER_URI`, `AUTH0_AUDIENCE` e `CORS_ALLOWED_ORIGIN`.
