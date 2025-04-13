# JDBC DAO

Projeto de estudo do padrão DAO utilizando JDBC.

## Descrição

Este projeto tem como objetivo praticar e entender o uso de JDBC em Java, implementando o padrão DAO (Data Access Object) para as entidades `Seller` e `Department`.

## Estrutura do Projeto

````markdown
jdbc-dao/
├── src/
│   ├── application/
│   │   └── Main.java
│   ├── db/
│   │   ├── DB.java
│   │   ├── DbException.java
│   │   └── DbIntegrityException.java
│   └── model/
│       ├── dao/
│       │   ├── DaoFactory.java
│       │   ├── SellerDao.java
│       │   └── DepartmentDao.java
│       ├── entities/
│       │   ├── Seller.java
│       │   └── Department.java
│       └── impl/
│           ├── SellerDaoJDBC.java
│           └── DepartmentDaoJDBC.java
├── db.properties
└── .gitignore, .iml, etc.
```

````

## Tecnologias

- Java 8+
- JDBC
- Banco de dados MySQL (ou outro compatível com JDBC)
- IDE IntelliJ IDEA (configuração `.iml`)

## Configuração

1. Clone este repositório:
   ```bash
   git clone https://github.com/murilosolino/jdbc-dao.git
   ```
2. Abra o projeto em sua IDE favorita.
3. Adicione o driver JDBC ao classpath do projeto.
   Exemplo (se usar Maven):
   ````xml
   <dependency>
     <groupId>mysql</groupId>
     <artifactId>mysql-connector-java</artifactId>
     <version>8.0.33</version>
   </dependency>
   ````
4. Crie um arquivo `db.properties` na raiz do projeto com as suas credenciais de conexão:
   ```properties
   db.url=jdbc:mysql://localhost:3306/seu_banco
   db.user=seu_usuario
   db.password=sua_senha
   ```
5. Crie o schema e as tabelas no seu banco de dados. Exemplo:
   ```sql
   CREATE TABLE department (
     Id INT AUTO_INCREMENT PRIMARY KEY,
     Name VARCHAR(60) NOT NULL
   );

   CREATE TABLE seller (
     Id INT AUTO_INCREMENT PRIMARY KEY,
     Name VARCHAR(100) NOT NULL,
     Email VARCHAR(100),
     BirthDate DATE,
     BaseSalary DOUBLE,
     DepartmentId INT,
     FOREIGN KEY (DepartmentId) REFERENCES department(Id)
   );
   ```
6. Popule as tabelas com dados de teste, se desejar.

## Como Executar

Na IDE, execute a classe `application.Main`. Ela vai testar os métodos:
- `findById`
- `findByDepartment`
- `findAll`
- `insert`
- `update`
- `deleteById`

## Status

- ✅ Implementação completa de `SellerDaoJDBC`
- ✅ Implementação completa de `DepartmentDao`

## Notas

 - ⚠️ O arquivo `application.Main` com os testes não foi desenvolvido para executar todos ao mesmo tempo. Sinta-se à vontade para comentar alguns blocos de testes e usar da maneira que preferir.

*Desenvolvido por Murilo Solino*

