# Synthetic Consciousness — Java Demonstration

A Java implementation of a five-axiom model of machine consciousness, paired
with a foundational binary neuron memory architecture. Together the two classes
form an educational demonstration of how consciousness-like behaviour can be
modelled computationally — from the lowest level of a single neuron reading and
writing binary state, up to a complete cognitive cycle integrating perception,
attention, imagination, planning, and emotion.

---

## Table of Contents

- [Conceptual Background](#conceptual-background)
- [The Five Axioms](#the-five-axioms)
- [Classes at a Glance](#classes-at-a-glance)
- [Memory and the Neuron](#memory-and-the-neuron)
- [SyntheticConsciousness](#syntheticconsciousness)
  - [The Consciousness Cycle](#the-consciousness-cycle)
  - [NeuralStateSpace and Learning](#neuralstatespace-and-learning)
  - [AttentionMechanism](#attentionmechanism)
  - [ImaginationEngine](#imaginationengine)
  - [PlanningModule](#planningmodule)
  - [EmotionalSystem](#emotionalsystem)
  - [Environment and Actions](#environment-and-actions)
- [Running the Demos](#running-the-demos)
- [Architecture Diagram](#architecture-diagram)
- [Extending the Model](#extending-the-model)
- [Academic Use](#academic-use)

---

## Conceptual Background

What would it mean for a machine to be conscious? This is one of the most
contested questions in both philosophy and artificial intelligence. One
influential approach — rather than attempting to resolve the philosophical
debate — takes a pragmatic engineering stance: identify the functional
properties that any conscious system demonstrably exhibits, express them
as formal axioms, and then build a machine that satisfies all five.

The result is not a claim that the machine *is* conscious in the philosophical
sense. It is a claim that the machine behaves in all the ways that a conscious
system behaves — that it models the world, attends to salient features,
simulates futures it has not experienced, selects actions on the basis of
those simulations, and maintains an affective state that colours all of the
above. Whether that constitutes "real" consciousness is left as an open
question. The engineering is tractable regardless of the answer.

This demonstration implements that five-axiom framework in Java, grounding it
in a concrete binary neuron memory model that illustrates how the lowest-level
substrate — a neuron reading and writing bits — connects to the highest-level
cognitive behaviour.

---

## The Five Axioms

The model is built around five properties that a conscious system must exhibit.
Each axiom corresponds directly to one component of `SyntheticConsciousness`:

### Axiom 1 — Presence
The system must have an inner representational world — an internal model of
the state of its environment and its own state within it. Without this, there
is no "something it is like" to be the system: it is merely a reflexive
input-output mapping with no inner world at all.

*Implemented by:* `NeuralStateSpace.perceive()` — maps sensor input into a
discrete internal state representation.

### Axiom 2 — Attention
Consciousness is always *of* something in particular. A conscious system does
not process all available information equally; it selects the most salient
features and amplifies them, with the selection itself modulated by the
system's current emotional state.

*Implemented by:* `AttentionMechanism.focus()` — weights input channels by
salience and emotional bias.

### Axiom 3 — Imagination
A conscious system can simulate states of the world that are not currently
present — it can imagine futures, counterfactuals, and alternatives. This
internal simulation capability is what allows planning to be more than
reflexive reaction.

*Implemented by:* `ImaginationEngine.simulate()` — generates a set of
plausible future neural states by perturbing the current state.

### Axiom 4 — Planning
The system must be able to use its imagined futures to select actions. Rather
than simply reacting to the current state, it evaluates the consequences of
possible actions across the imagined scenarios and chooses the action most
likely to lead to a valued outcome.

*Implemented by:* `PlanningModule.selectAction()` — scores each action across
the imagined futures, weighted by the current emotional state.

### Axiom 5 — Emotion
A conscious system maintains an ongoing affective evaluation of its
interactions with the world. This emotional state is not ornamental — it
provides a continuous signal of how well or badly things are going, and it
feeds back into attention and planning, biasing the system towards actions
that have historically been rewarding.

*Implemented by:* `EmotionalSystem.update()` — updates mood via a simple
reward-prediction signal, bounded in the range [-1, +1].

---

## Classes at a Glance

| Class | Role |
|---|---|
| `Memory` | Binary addressable memory backed by a `byte[]` array |
| `Memory.Neuron` | Inner class; the neuron that reads and writes individual memory cells |
| `SyntheticConsciousness` | Top-level coordinator; runs the five-axiom consciousness cycle |
| `NeuralStateSpace` | Stores and learns from perceived states of the world |
| `NeuralState` | Discrete representation of one moment of perception |
| `AttentionMechanism` | Axiom 2 — selectively amplifies salient input features |
| `ImaginationEngine` | Axiom 3 — generates simulated future states |
| `PlanningModule` | Axiom 4 — selects actions by evaluating imagined futures |
| `EmotionalSystem` | Axiom 5 — maintains affective mood state from reward signals |
| `Environment` | Simulated world: produces sensor data, executes actions, returns rewards |
| `Action` | Enum of four possible actions: APPROACH, AVOID, EXPLORE, REST |

---

## Memory and the Neuron

`Memory` is the foundational class. It models a binary addressable memory
space of size 2^n bytes — for n=8, that is 256 cells, one per neuron state:

```java
Memory mem = new Memory(8);
System.out.println("Memory size = " + mem.getMemorySize()); // 256
```

Each cell stores a single bit as a Java `byte` (0 or 1). The inner `Neuron`
class provides the read/write interface that a neuron in the network would use:

```java
Neuron neuron = mem.getNeuron();

// Write a bit at address 42
neuron.setMem(true, 42);

// Read it back
byte value = neuron.readMem(42);  // returns 1
```

Both `readMem` and `setMem` perform bounds checking and throw
`IndexOutOfBoundsException` for invalid addresses.

The memory dump utility prints the full contents of the array to stdout as a
raw binary string, useful for inspecting the state of the entire memory space:

```java
mem.dumpMemory(); // prints 256 0s and 1s
```

### Why binary?

The binary model is deliberate. A real biological neuron fires or does not
fire — it is the simplest possible unit of information. Building the memory
from individual bit cells rather than floating-point values keeps the model
honest about what the substrate actually is: a vast collection of binary
switches. The higher-level behaviour in `SyntheticConsciousness` emerges from
the patterns formed across those switches, not from any property of the
individual cell.

---

## SyntheticConsciousness

`SyntheticConsciousness` is the top-level class. It instantiates all five
axiom components and drives the consciousness cycle. The state space size
parameter controls how many distinct perceived states the system can
distinguish:

```java
SyntheticConsciousness consciousness = new SyntheticConsciousness(100);
consciousness.run(5); // run 5 consciousness cycles
```

### The Consciousness Cycle

Each call to `consciousCycle()` steps through all five axioms in sequence:

```
Sensor input
     │
     ▼
[1. PRESENCE]   perceive() → NeuralState
     │
     ▼
[2. ATTENTION]  focus() → weighted input (modulated by mood)
     │
     ▼
[3. IMAGINATION] simulate() → array of possible future states
     │
     ▼
[4. PLANNING]   selectAction() → best Action across imagined futures
     │
     ▼
[5. EMOTION]    update() → reward signal updates mood
     │
     ▼
  learn() → reinforcement update on state value
```

Each stage prints its result to stdout, producing a readable trace of the
system's reasoning:

```
=== Consciousness Cycle ===
1. PRESENCE:    Current state - State[0.43, -0.12, 0.87...]
2. ATTENTION:   Focused on features - [0.43, -0.11, 0.92, ...]
3. IMAGINATION: Simulated 3 possible futures
   Scenario 1: State[0.51, -0.09, 0.84...]
   Scenario 2: State[0.38, -0.14, 0.91...]
   Scenario 3: State[0.47, -0.08, 0.88...]
4. PLANNING:    Selected action - EXPLORE
5. EMOTION:     Reward=0.312, Mood=0.062
```

### NeuralStateSpace and Learning

`NeuralStateSpace` maps continuous sensor vectors to discrete `NeuralState`
objects by discretising the input values to one decimal place. States are
stored in a `HashMap` alongside learned value estimates.

After each action, the state value is updated using a simple reinforcement
learning rule:

```
value(state) = value(state) + learningRate × reward
```

with `learningRate = 0.1`. Over many cycles the system builds up a value
function that reflects which states have historically been associated with
positive outcomes.

### AttentionMechanism

Attention weights each input channel by its absolute magnitude (salience)
and scales that weight by the current emotional state:

```
attentionWeight = |input[i]| × (1.0 + mood × 0.5)
focused[i]      = input[i]  × min(1.0, attentionWeight)
```

A positive mood increases attention to salient stimuli; a negative mood
reduces it. This mirrors the well-documented effect of affective state on
selective attention in biological cognition.

### ImaginationEngine

The imagination engine generates `numScenarios` alternative future states by
perturbing the current state with Gaussian noise (standard deviation 0.1).
This is a minimal model of what simulation requires — the ability to produce
plausible variations on the current world model without actually changing
the world.

In a more sophisticated implementation, these simulated states could be
generated by a learned transition model rather than random perturbation.

### PlanningModule

The planner evaluates each of the four possible actions by estimating the
value of the imagined future states and adding an emotional bias:

```
estimatedValue(action) = mean(value(imagined states)) + mood × 0.3
```

The action with the highest estimated value is selected. Because all imagined
states start with zero learned value, early behaviour is determined primarily
by the emotional bias and random variation in the imagination. As the system
learns through repeated cycles, the state values become informative and
planning becomes increasingly goal-directed.

### EmotionalSystem

Mood is updated each cycle using a prediction-error signal analogous to
dopaminergic reward signalling in biological systems:

```
delta = reward − baseline           (where baseline = 0)
mood  = mood × 0.8 + delta × 0.2
mood  = clamp(mood, -1.0, +1.0)
```

The system is thus not simply reacting to the raw reward but to the
*difference* between the reward and its baseline expectation. Unexpected
positive outcomes increase mood more than expected ones; unexpected negative
outcomes decrease it more.

### Environment and Actions

The `Environment` class provides a simulated five-dimensional world. Its
state drifts over time with Gaussian noise, and rewards are assigned by the
sign of the first state dimension relative to the chosen action:

| Action | Reward condition |
|---|---|
| `APPROACH` | +1.0 if state[0] > 0, −0.5 otherwise |
| `AVOID` | +1.0 if state[0] < 0, −0.5 otherwise |
| `EXPLORE` | Random in [0, 0.5) |
| `REST` | Fixed +0.2 |

This creates a dynamic where the optimal action depends on the current
environmental state — exactly the kind of problem where perception, attention,
imagination, and planning all contribute meaningfully to performance.

---

## Running the Demos

### Prerequisites

- Java 8 or later (JDK)
- No external dependencies

### Compile and run `Memory`

```bash
javac Memory.java
java Memory
```

Expected output:
```
Memory size = 256
0000000000000000... (256 zeros)
```

### Compile and run `SyntheticConsciousness`

```bash
javac SyntheticConsciousness.java
java SyntheticConsciousness
```

Expected output pattern (values are random each run):
```
Starting Synthetic Consciousness Demonstration
Based on Five Axioms of Machine Consciousness

--- Cycle 1 ---

=== Consciousness Cycle ===
1. PRESENCE:    Current state - State[0.43, -0.12, 0.87...]
2. ATTENTION:   Focused on features - [0.43, -0.11, 0.92, ...]
3. IMAGINATION: Simulated 3 possible futures
   Scenario 1: State[0.51, -0.09, 0.84...]
   Scenario 2: State[0.38, -0.14, 0.91...]
   Scenario 3: State[0.47, -0.08, 0.88...]
4. PLANNING:    Selected action - EXPLORE
5. EMOTION:     Reward=0.312, Mood=0.062

--- Cycle 2 ---
...
```

Each cycle is separated by a one-second pause to make the output readable.

---

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    SyntheticConsciousness                    │
│                                                             │
│  Environment ──sensor data──▶ NeuralStateSpace             │
│       │                            │                        │
│       │                      NeuralState                   │
│       │                            │                        │
│       │                    AttentionMechanism               │
│       │                      (Axiom 2)                      │
│       │                            │                        │
│       │                    ImaginationEngine                │
│       │                      (Axiom 3)                      │
│       │                            │                        │
│       │                     PlanningModule                  │
│       │                      (Axiom 4)                      │
│       │                            │                        │
│       ◀──────────Action────────────┘                        │
│       │                                                     │
│      reward ──────────────▶ EmotionalSystem                 │
│                               (Axiom 5)                     │
│                                   │                         │
│                              mood feedback                  │
│                            ▶ Attention                      │
│                            ▶ Planning                       │
│                                                             │
└─────────────────────────────────────────────────────────────┘

Substrate layer (Memory.java):
┌──────────────────────────────┐
│  Memory (2^n byte array)     │
│  └── Neuron                  │
│       ├── readMem(address)   │
│       └── setMem(bit, addr)  │
└──────────────────────────────┘
```

---

## Extending the Model

The current implementation is deliberately minimal to keep the five axioms
clearly visible. Several natural extensions would make it more capable:

**Learned transition model** — replace the random Gaussian perturbation in
`ImaginationEngine` with a learned model of how states transition in response
to actions. This would make the simulated futures genuinely predictive rather
than merely random.

**Episodic memory** — connect `Memory.java` directly to `NeuralStateSpace`
so that past states and their associated rewards are stored in the binary
neuron memory and can be replayed during the imagination phase.

**Deeper planning horizon** — extend `PlanningModule` to simulate multiple
steps ahead rather than a single-step lookahead.

**Richer action space** — replace the four-action `Action` enum with a
continuous action space, which would require a policy network rather than
a simple argmax over estimated values.

**Hebbian learning in `Memory`** — implement the classic Hebbian rule
(neurons that fire together wire together) at the `Neuron` level, so that
the memory substrate itself adapts to patterns of use rather than treating
all addresses symmetrically.

---

## Academic Use

This code is provided freely for educational and research use. No licence
is required for use in academic institutions, student projects, coursework,
or non-commercial research. If you use or adapt this material in published
work, an acknowledgement is appreciated but not required.

**Contact:** mariogianota@protonmail.com  
**Copyright © Mario Gianota. All rights reserved (non-academic use).**
