# SyntheticConsciousness

A Java implementation of **Igor Aleksander's Five Axioms of Consciousness** — a theoretical framework proposing that machine consciousness can be characterised by five fundamental properties. This project provides an educational, runnable model of each axiom working together in a continuous perception–action loop.

---

## Background

Igor Aleksander, Emeritus Professor of Neural Systems Engineering at Imperial College London, proposed that any system capable of consciousness must exhibit five core properties. His work, developed through the MAGNUS neural architecture, influenced both AI research and the philosophy of mind.

The five axioms are:

| # | Axiom | Description |
|---|-------|-------------|
| 1 | **Presence** | The system perceives and represents the world as it currently is |
| 2 | **Attention** | The system selectively focuses on salient aspects of its input |
| 3 | **Imagination** | The system can simulate states beyond those currently perceived |
| 4 | **Planning** | The system uses imagined futures to guide present action |
| 5 | **Emotion** | The system evaluates outcomes affectively and updates its internal state |

---

## Architecture

Each axiom is implemented as a discrete Java class. A central `SyntheticConsciousness` object coordinates them in a repeating conscious cycle.

```
┌─────────────────────────────────────────────────┐
│               Conscious Cycle                   │
│                                                 │
│  Environment ──► NeuralStateSpace (Presence)    │
│                        │                        │
│                  AttentionMechanism              │
│                        │                        │
│                  ImaginationEngine               │
│                        │                        │
│                  PlanningModule                  │
│                        │                        │
│                  EmotionalSystem ◄── Reward      │
│                        │                        │
│              Action ──► Environment             │
└─────────────────────────────────────────────────┘
```

### Classes

| Class | Axiom | Role |
|-------|-------|------|
| `NeuralStateSpace` | Presence | Maps sensor inputs to discrete neural states; learns state values via reinforcement |
| `NeuralState` | Presence | A discretised snapshot of a perceptual pattern |
| `AttentionMechanism` | Attention | Weights input features by salience and current emotional bias |
| `ImaginationEngine` | Imagination | Generates plausible future states by perturbing the current state |
| `PlanningModule` | Planning | Evaluates imagined futures and selects the highest-value action |
| `EmotionalSystem` | Emotion | Maintains a mood value (−1 to +1) updated by prediction error |
| `Environment` | — | Simulates a stochastic world; issues rewards in response to actions |
| `Action` | — | Enum of possible behaviours: `APPROACH`, `AVOID`, `EXPLORE`, `REST` |

---

## Requirements

- Java 8 or later

---

## Compile & Run

```bash
javac SyntheticConsciousness.java
java  SyntheticConsciousness
```

The program runs five conscious cycles by default, printing the output of each axiom at every step.

---

## Sample Output

```
Starting Synthetic Consciousness Demonstration
Based on Igor Aleksander's Five Axioms

--- Cycle 1 ---

=== Consciousness Cycle ===
1. PRESENCE:    Current state - State[0.23, -0.41, 0.07...]
2. ATTENTION:   Focused on features - [0.23, -0.38, 0.06, ...]
3. IMAGINATION: Simulated 3 possible futures
   Scenario 1: State[0.31, -0.39, 0.14...]
   Scenario 2: State[0.19, -0.44, 0.02...]
   Scenario 3: State[0.27, -0.35, 0.09...]
4. PLANNING:    Selected action - EXPLORE
5. EMOTION:     Reward=0.34, Mood=0.068
```

---

## Configuring the Simulation

To change the number of cycles, edit `main()`:

```java
consciousness.run(10);  // run 10 cycles instead of 5
```

To change the size of the neural state space:

```java
SyntheticConsciousness consciousness = new SyntheticConsciousness(200);
```

A larger state space allows more distinct perceptual states to be stored and valued, at the cost of slower convergence in the reinforcement learning component.

---

## Limitations

This is an **educational model**, not a production AI system. Several simplifications are made:

- **State discretisation** is coarse — real neural architectures use continuous or high-dimensional representations.
- **Imagination** perturbs the current state with Gaussian noise rather than learning a true world model.
- **Planning** performs a shallow single-step lookahead; no tree search or rollout is implemented.
- **Reinforcement learning** uses a basic scalar update with no temporal difference or eligibility traces.
- The model does not claim to instantiate genuine machine consciousness — it demonstrates the *structural* relationships between Aleksander's axioms.

---

## Further Reading

- Aleksander, I. & Morton, H. — *Neurons and Symbols: The Stuff That Mind Is Made Of* (1993)
- Aleksander, I. — *How to Build a Mind* (2000)
- Aleksander, I. — *The World in My Mind, My Mind in the World* (2005)
- [MAGNUS architecture overview](https://en.wikipedia.org/wiki/Igor_Aleksander)

---

## License

This project is licensed under the **GNU General Public License v3.0 (GPL-3.0)**.

You are free to use, study, modify, and distribute this software, provided that any derivative works are also distributed under the same GPL-3.0 licence. There is no warranty for this program to the extent permitted by applicable law.

See the [LICENSE](./LICENSE) file for the full licence text, or visit [gnu.org/licenses/gpl-3.0](https://www.gnu.org/licenses/gpl-3.0.html).
