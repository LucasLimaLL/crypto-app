# crypto-app — Base de Conhecimento

> Lido automaticamente pelo Claude Code em toda sessão.

## O que é este projeto

Aplicativo Android do sistema Criptograma.
Consome a biblioteca `crypto-shared` (publicada via `mavenLocal`).

## Stack

    Android (minSdk 24 / compileSdk 35) + Java 21
    Navigation Component 2.8.4
    ViewModel + LiveData (lifecycle 2.8.7)
    ViewBinding
    Checkstyle 10.21.4 (via checkstyleMain / checkstyleTest / checkstyleAndroidTest)
    JUnit 4 + Mockito 5 + InstantTaskExecutorRule (testes unitários JVM)
    Espresso (testes instrumentados)

## Estrutura

    Arquitetura vertical por feature (vertical slice)
    app/src/main/java/br/com/lucaslima/cryptogram/
      ├── feature/auth/    ← login: data · domain · ui
      └── feature/home/   ← home hub: data · domain · ui

## Regras obrigatórias

    → Zero comentários: nem //, nem /* */, nem Javadoc — código se autodocumenta
    → Checkstyle falha o build se houver comentário em qualquer .java
    → Testes unitários para toda lógica pública (use cases, view models, domain)
    → Testes de integração no pacote integration/ com objetos reais (sem mocks)
    → Rodar testes: .\gradlew testDebugUnitTest --no-daemon --offline -g .gradle-user-home
    → NÃO commitar .gradle-user-home/ (está no .gitignore)

## Comandos

    .\gradlew testDebugUnitTest --no-daemon --offline -g .gradle-user-home   ← testes JVM
    .\gradlew checkstyleMain --no-daemon --offline -g .gradle-user-home      ← lint

## Convenção de commits

    Escopos: auth | home | puzzle | shared | infra | test

    feat(home): implementar tela de seleção de puzzle
    test(auth): cobrir fluxo de credenciais inválidas
    fix(puzzle): corrigir índice fora de limite no GameBoard
