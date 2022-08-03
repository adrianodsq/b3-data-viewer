# Banco de dados
### Crie um docker 

```
docker run --rm   --name pg-docker -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 -v $HOME/docker/volumes/postgres:/var/lib/postgresql/data  postgres
```

Acesse o banco criado e execute o script `b3-data-viewer/back-end/database_init.sql` para criar a estrutura do banco.

# Back-end

Configure o caminho dos arquivos de input no arquivo application.properties
Rode a aplicação com o comando `./gradelw bootRun` - a aplicação vai subir na porta 8080

# Front-end

Verifique as dependencias do projeto, talvez seja necessario instalar alguns pacotes via npm.
```
npm install express

npm install axios
``` 
Execute a aplicação com o comando `node server.js`. Esta aplicação sobe na porta 8785

Assim que a app subir é só acessar no navegador:

Ações: http://localhost:8785/

Fiis: http://localhost:8785/fiis

Para importar os arquivos de dados execute:
curl -X POST http://localhost:8080/b3/fiis/import-all

curl -X POST http://localhost:8080/b3/acoes/import-all
