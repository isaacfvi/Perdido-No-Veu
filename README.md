# 🏚️ Perdido-No-Veu

### *Geração Procedural de Ambientes Internos e Comportamento Adaptativo de NPCs*

Este projeto implementa um jogo digital 2D de exploração ambientado em uma **mansão assombrada**, desenvolvido como parte do **Trabalho de Conclusão de Curso** do Bacharelado em Engenharia da Computação — UTFPR.

O jogo combina técnicas de **Geração Procedural de Conteúdo (PCG)** e **Inteligência Artificial** para criar ambientes dinâmicos e um antagonista (fantasma) capaz de reagir ao jogador de forma adaptativa.


## Vídeo Demonstrativo do Jogo

[![Assistir no YouTube](https://img.youtube.com/vi/KfF3wb2rrvQ/0.jpg)](https://youtu.be/KfF3wb2rrvQ)

---

## 🎯 Objetivo do Projeto

Criar um jogo de exploração que utilize:

- **Geração procedural** para montar automaticamente um layout único de mansão a cada partida.
- **A\*** (A-star) para planejamento de caminhos do NPC fantasma.
- **Máquina de Estados (FSM)** para controlar o comportamento adaptativo do fantasma.
- **Heurísticas de pós-processamento** para garantir coerência arquitetônica e navegabilidade.

---

## 🧩 Principais Funcionalidades

### 🏗️ 1. Geração Procedural da Mansão
- Divisão do mapa utilizando **Binary Space Partitioning (BSP)**.
- Uso de **grafos** para conexão e organização dos cômodos.
- Parâmetros configuráveis (seed, área mínima, proporção).
- Geração de layouts coerentes, conectados e navegáveis.

---

### 👻 2. Comportamento Adaptativo do Fantasma
- Inteligência controlada por uma **Finite State Machine (FSM)**.
- Estados como:
    - Patrulha
    - Caça
    - Perseguição
    - Fuga
- Sensores que detectam:
    - linha de visão,
    - ruídos,
    - rastro do jogador.

---

### 🧭 3. Pathfinding com A\*
- Implementação do algoritmo A\*.
- Suporte às heurísticas Manhattan, Euclidiana e Chebyshev.
- Replanejamento constante do caminho durante a perseguição.

---

### 🎨 4. Renderização e Arte
- Sistema de tiles em **LibGDX**.
- Tileset autoral criado em Aseprite.
- Conversão da matriz de geração para mapa jogável.

---

## 🛠️ Tecnologias Utilizadas

| Componente | Tecnologia |
|-----------|------------|
| Linguagem | Java 17 |
| Framework | LibGDX |
| IDE | IntelliJ IDEA |
| Arte | Aseprite |
| Versionamento | Git |
| Ambiente | JVM |

---
