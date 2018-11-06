# Click<span style="color:yellow">Bus</span>
Esta é uma aplicação de exercício. 

## Builds prontos

Caso queira executar a aplicação antes de fazer o seu próprio build, utilize o pacote disponível [aqui](https://bitbucket.org/thiagosoaresjr/clickbusrestapi/downloads/clickbus.war)

Para executar a aplicação, apenas execute o seguinte comando:

Desenvolvimento:

    java -jar clickbus.war

Produção:

    java -jar clickbus.war --spring.profiles.active=prod

Para executar a aplicação em qualquer *profile* será necessário o **elasticSearch**. 

Para **Desenvolvimento**, a  configuração mais básica será o suficiente e o Docker é uma boa solução para isso. Veja como [aqui](#elasticdocker)


Para executar o *profile* de **Produção** será necessita configurar o banco de dados e o **elasticSearch** como serviços ou com o Docker, como será descrito a seguir neste documento. 


Após iniciada a aplicação, navegue para [http://localhost:8080](http://localhost:8080) e você terá acesso ao link para a documentação [Swagger](http://localhost:8080/#/docs)

## Validação do exercício proposto 

Além dos requisitos solicitados no exercício, algumas outras coisas foram criadas. Para a validação da tarefa é suficiente utilizar os seguintes *resources*:

- [/api/authenticate]()
- [/api/authenticate]()
- [/api/authenticate]()

[TET](#visualtesting)


## Requisitos para desenvolvimento
Esta aplicação foi homologada com o seguinte ambiente:
 
 - Java 8
 - [Maven 3.5.0](https://maven.apache.org/download.cgi)
 - [Node.js 8.11.3](https://nodejs.org/en/) (não funcionará com o NodeJs 10.x)
 - [Yarn 1.9.4](https://yarnpkg.com/lang/en/docs/install/#debian-stable)
 
 Estes são os requisitos obrigatórios, mas o Docker também poderá ser utilizado, opcionalmente, como veremos mais a frente. 

## Desenvolvimento

Antes de *buildar* este projeto, você deve instalar e configurar Node.js e o Yarn em sua estação:

1. Node.js: Usamos o Node para executar um servidor da Web de desenvolvimento e construir o projeto.
2. Yarn: Usamos o Yarn para gerenciar as dependências do Node.

Depois de instalar o Node.js, você poderá executar o seguinte comando para instalar ferramentas de desenvolvimento. 
Você só precisará executar este comando logo após o clone do projeto ou quando as dependências mudarem no package.json.

    yarn install

Execute os seguintes comandos **em dois terminais separados** para iniciar a aplicação back-end e front-end. 
Dessa forma você terá uma boa experiência de desenvolvimento, onde a aplicação e seu navegador
serão atualizados automaticamente conforme os arquivos são alterados.

    ./mvnw
    yarn start

Após o comando `./mvnw`, a interface do projeto será exibida no endereço [http://localhost:8080](http://localhost:8080). 
Após o comando `yarn start`, o browser exibirá o interface do projeto no endereço [http://localhost:9000](http://localhost:9000). Este é o seu endereço para desenvolvimento do front-end.


#### Troubleshooting#1
Após o clone do projeto ou um `clean` ser executado, o webpack precisará reconstruir algumas estruturas da visão que são utilizadas no ambiente de desenvolvimento, livereload etc. Para isso, utilize o seguinte comando 
para iniciar o back-end da aplicação:

	./mvnw -P webpack

As demais vezes em que o back-end for iniciado, não será mais necessário utilizar esse profile específico. 

Isso não será necessário caso o front-end seja inicado com o yarn. Utilizando o comando `yarn start` para iniciar o front-end.


 #### Troubleshooting#2 
 
 Durante o build do projeto, podem ocorrer problemas com uma dependência chamada **node-sass** [[BUG](https://github.com/sass/node-sass/issues/2032)].
Caso isso ocorra, utilize o seguinte comando:

    yarn

Ele fará um reBuild da arvore de dependências da visão. 


## Bancos de dados

Esta aplicação utiliza dois bancos de dados. 
Para desenvolvimento, no profile **dev**, um bando H2 armazenará os dados no disco, dentro da pasta target do projeto. 
Para a produção, um banco **MySql** será utilizado. As credenciais desse banco devem ser configuradas no arquivo [application-prod.yml](/src/main/resources/config/application-prod.yml). Este arquivo está configurado para que o Docker forneca este banco de dados.

## ElasticSearch

Esta aplicação utiliza o ElasticSearch em suas consultas.
Para **desenvolvimento**, ele utiliza uma base temporária dentro do projeto, contudo é necessário que o serviço esteja disponível. 
Isso poderá ser feito com o Docker conforme pode ser visto a seguir. 

Para **produção** é necessário um serviço Elastisearch funcionando e com sua interface Rest habilidata, visto que a comunicação acontecerá através do [JEST](https://github.com/VanRoy/spring-data-jest). 

## Build para Produção

Para otimizar a aplicação para o ambiente de produção, utilize os comando:

    ./mvnw -Pprod clean package

Ele irá concatenar e minificar todos os CSS e JavaScripts do cliente e atualizar as referências no `index.html`.

Então, para saber se tudo esta funcionando bem, execute o `war` da aplicação rodando: 

    java -jar target/clickbus.war


Então navegue para [http://localhost:8080](http://localhost:8080) e veja a mágica. Claro, seria legal configurar o banco de dados antes.

## Testing

Para disparar os testes da aplicação, use:

    ./mvnw clean test

## <a name="visualtesting"></a> Visual Testing

validacao do exercicio

```
curl -X POST \
  http://localhost:8080/api/authenticate \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 80cab4e0-ffcb-4b3f-ab06-12ab78c8cd59' \
  -d '{
  "username":"admin", "password":"admin"
}'
```

```
curl -X GET \
  'http://localhost:8080/api/places?eagerload=true' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU0MTU4OTg4Mn0.eoB0WNKjhoPZbCRQStL3aNu9aVJyuRm5gQohpGu3Nz5sn7mdaZf0H9mL2oMdumJfStL1buL1WOXC1CAb-UKqFg' \
  -H 'Cache-Control: no-cache' \
  -H 'Postman-Token: eefc5a81-e01c-43e5-8724-b98e883c7a19'
```

```
curl -X GET \
  http://localhost:8080/api/places/1/details \
  -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU0MTU4OTg4Mn0.eoB0WNKjhoPZbCRQStL3aNu9aVJyuRm5gQohpGu3Nz5sn7mdaZf0H9mL2oMdumJfStL1buL1WOXC1CAb-UKqFg' \
  -H 'Cache-Control: no-cache' \
  -H 'Postman-Token: 9d7da9a9-f1d1-4583-805f-f1baa0089153'
```



## (#elasticdocker)  Usando o Docker para facilitar o desenvolvmento (opicional)

Você pode usar o Docker para melhorar sua experiência de desenvolvimento do JHipster. Várias configurações do docker-compose estão disponíveis na pasta [src/main/docker](src/main/docker) para iniciar os serviços necessários para a aplicação.

Por exemplo, para iniciar um banco de dados Mysql em um contêiner docker, execute:

    docker-compose -f src/main/docker/mysql.yml up -d

Para pará-lo e remover o contêiner, execute:

    docker-compose -f src/main/docker/mysql.yml down

O mesmo procedimento poderá ser utilizado para iniciar o serviço do ElastcSearch

```
docker-compose -f src/main/docker/elasticsearch.yml up -d
```

```
docker-compose -f src/main/docker/elasticsearch.yml down
```

Você também pode *dockerizar* totalmente seu aplicativo e todos os serviços dos quais ele depende. Para conseguir isso, primeiro crie uma imagem do docker do seu aplicativo executando:

    ./mvnw verify -Pprod dockerfile:build dockerfile:tag@version dockerfile:tag@commit

E então execute:

    docker-compose -f src/main/docker/app.yml up -d

No final, uma imagem Docker de sua aplicação será criada e poderá ser *deployada* onde for necessário.

 ### Code quality

O Sonar é utilizado para análise do código-fonte. Você poderá iniciar um [Servidor Sonar local](http://localhost:9001) da seguinte forma:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

Então faça a análise:

```
./mvnw -Pprod clean test sonar:sonar
```


