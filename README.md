# TAUROS - Tecnologia Aplicada à Unidade de Resolução Para Ocorrências

## Apresentação do Sistema

TAUROS é um sistema web de controle e gerenciamento de Chamados/Ocorrências. 

## Linguagem utilizada

- Java (versão 11)

## Tecnologias Utilizadas

- Spring MVC
- Spring Security
- Thymeleaf


## Banco de dados

- Postgresql

## Diagrama de Classes

![image](https://github.com/mario-estevam/Tauros/assets/80980660/4eba247f-9145-4196-8ac5-7e0db2750595)



![image](https://github.com/mario-estevam/Tauros/assets/80980660/b7ec3ebf-641b-42a2-923c-9b2a2ff1c7ea)


## Requisitos Funcionais

O sistema é dividido em quatro módulos:
- Administrador: Neste módulo é possível fazer gestão geral, de setores,
problemas, chamados, usuários etc;

- Responsável do setor: Neste módulo é possível fazer gestão
problemas do próprio setor, chamados do próprio setor e usuários do
próprio setor;

- Requisitante: Neste módulo é possível apenas abrir, visualizar e editar
os próprios chamados;

- Operador: Neste módulo é possível apenas abrir, visualizar e editar os
próprios chamados e atender aos chamados de seu setor;

**Administrador**
   ```
    RF01: O sistema deve permitir criar, alterar, habilitar, desabilitar
    e excluir um usuário do sistema;
    
    RF02: O sistema deve permitir criar, visualizar, alterar e excluir
    um Setor;
    
    RF03: O sistema deve permitir criar, visualizar, alterar e excluir
    um Problema;
    
    RF04: O sistema deve permitir criar, visualizar, atender, alterar e
    excluir um Chamado;
    
    RF05: O sistema deve permitir a visualização de todos os
    chamados (Concluídos, Em atraso, Em andamento, Abertos);
    
    RF06: O sistema deve permitir a visualização de todos os
    setores;
    
    RF07: O sistema deve permitir a visualização de todos os
    problemas;
    
    RF08: O sistema deve permitir a visualização relatórios;
    Responsável do setor;
    
   
```

**Responsável do setor**

```
    RF01: O sistema deve permitir criar, alterar e desabilitar um
    usuário do sistema;

    RF02: O sistema deve permitir criar, alterar, visualizar e excluir
    um problema de seu setor;

    RF03: O sistema deve permitir criar, alterar, atender, visualizar e
    excluir um chamado de seu setor;

    RF04: O sistema deve permitir a visualização dos chamados de
    seu setor(Concluídos, Em atraso, Em andamento, Abertos);

    RF08: O sistema deve permitir a visualização relatórios do seu
    setor;
```

**Requisitante**
```
    RF01: O sistema deve permitir criar, alterar, visualizar e excluir
    um chamado próprio;
    
    RF02: O sistema deve permitir a visualização de seus chamados
    (Concluídos, Em atraso, Em andamento, Abertos);
```

**Operador**
```
    RF01: O sistema deve permitir criar, alterar, visualizar e excluir
    um chamado próprio;

    RF02: O sistema deve permitir a visualização de seus chamados
    e de seu setor (Concluídos, Em atraso, Em andamento, Abertos);

    RF03: O sistema deve permitir o atendimento de chamados de
    seu setor;
```

### Apresentação do Sistema

- Tela de Login

![image](https://github.com/mario-estevam/Tauros/assets/80980660/60ac58eb-cd9b-40c5-8f8e-ebc3b20044e4)


##### Administrador
- Página Inicial 

![image](https://github.com/mario-estevam/Tauros/assets/80980660/751e43cf-65ac-4a66-a77b-b5447ab991f0)


- Página de Listagem de Chamados

![image](https://github.com/mario-estevam/Tauros/assets/80980660/792455a4-7341-4586-be57-3d3c35fde48c)


- Página de visualização do Chamado

![image](https://github.com/mario-estevam/Tauros/assets/80980660/7a38f041-3777-4dd9-adc7-71b9f535097d)

- Página de Chamados em Atendimento com opção de visualizar ou concluir o chamado

![image](https://github.com/mario-estevam/Tauros/assets/80980660/7c46befc-eafb-4a19-ab9b-6b72859fe131)

- Página de listagem de usuários

![image](https://github.com/mario-estevam/Tauros/assets/80980660/e5f4c9c7-a435-4722-b479-f2b0f46b864c)

- Página de cadastro de Usuário

![image](https://github.com/mario-estevam/Tauros/assets/80980660/922cf605-a514-45dd-a2ce-86f00d4269e7)

- Página de Usuário Pendentes (usuários que se cadastraram mas ainda não tem acesos ao sistema)

![image](https://github.com/mario-estevam/Tauros/assets/80980660/9eca5b18-41a1-4865-9101-29c182c9fc51)

- Página de Listagem de Setores

![image](https://github.com/mario-estevam/Tauros/assets/80980660/7586b7e9-cdcc-44d3-a361-a7d262e73668)

- Página de Cadastro de Setor

![image](https://github.com/mario-estevam/Tauros/assets/80980660/a228002b-a9c5-4ae5-b8f1-c1e43a6133e0)

- Página de Listagem de Problemas

![image](https://github.com/mario-estevam/Tauros/assets/80980660/3ba503aa-cce2-47f1-b33e-c07f8c19969d)

- Página de Cadastro de um Problema

![image](https://github.com/mario-estevam/Tauros/assets/80980660/ac839f29-84ab-416f-a2a4-b04f83c21791)

- Página de Relatório por Setor com filtro Aplicado para Setor de Informática

![image](https://github.com/mario-estevam/Tauros/assets/80980660/bd953b94-bb53-45bb-83e8-5c504a9c2a12)


##### Responsável Pelo Setor 

- Página Inicial (Se difere apenas nos dados exibidos, onde ao administrador são exibidos dados relacionados a todos os setores, e no do Responsável pelo Setor os dados exibidos na página inicial são referentes apenas ao setor dele, e essa regra se aplica pras demais páginas)

![image](https://github.com/mario-estevam/Tauros/assets/80980660/2d1a704a-c198-491f-b2be-fbbf4d21ef85)

##### Operador

- Página Inicial (Os dados exibidos são referentes ao setor em que o Operador faz parte, nesse caso é o de Informática)

![image](https://github.com/mario-estevam/Tauros/assets/80980660/452d8bdc-fe0b-4155-a1f6-8aaf5407e77f)

- Modal para conclusão de atendimento ao chamado
![image](https://github.com/mario-estevam/Tauros/assets/80980660/f83ef436-56a8-4b32-a3d4-61416a617247)

- Página de Detalhes do Chamado (Chamado já concluído, com descrição do problema e solução)

![image](https://github.com/mario-estevam/Tauros/assets/80980660/023e8a1f-a606-4ddb-aa4c-a3c8c59d23fc)

##### Requisitante
- Página Inicial

![image](https://github.com/mario-estevam/Tauros/assets/80980660/bd0933e8-d1b0-4032-bfd2-7c007c135461)

- Página de abertura do Chamado

![image](https://github.com/mario-estevam/Tauros/assets/80980660/686827c4-1639-4ccd-b7f3-ca3ab9449657)










