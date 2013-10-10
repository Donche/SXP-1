---
title: Sigma Protocols
permalink: wiki/Sigma_Protocols/
layout: wiki
---

In general
----------

Sigma protocols involve a verifier (A) and prover (B). They suppose that
Alice and Bob share a public input \(v\) and agree on an efficiently
testable relation \(R\), and that Bob pretends to have a private input
\(w\):

-   such that \((v,w)\in R\);
-   which he does does not want to disclose.

Through the protocol, Bob will seek to convince Alice.

The protocol has three rounds:

\[B\rightarrow A: a=a(v,R,w)\]

\[A\rightarrow B: c=c(v,R,a)\]

\[B\rightarrow A: r=r(v,R,w,a,c,w)\] Lastly, Alice checks that Bob
response \(r\) to her challenge \(r\) is valid. This explains what the
last two rounds are for. The first round is there out of technical
necessity: Bob chooses this \(a\) as a mask for passing the challenge
without disclosing \(w\).

Building blocks
---------------

Here are examples of Sigma protocols based on the hardness of Discrete
Logarithms. Fix \(p\) a prime and \(g\) an integer. The powers of \(g\)
form a subgroup \(G\) inside the group \(Z_p\), which is that of
integers modulo \(p\). The choice of these \(p\) and \(g\) is important
so that they meet the [Decisional
Diffie-Hellman](http://en.wikipedia.org/wiki/Decisional_Diffie%E2%80%93Hellman_assumption)
assumption; but there are standard techniques for doing that.

**Ex. 1: Discrete Logarithm**

-   Public input \(v\in G\).
-   Agreed relation \((v,w)\in R \Leftrightarrow g^w=v\).
-   Private input \(w\).
-   Bob will need some random \(s\in Z_p\).
-   Alice will need some random \(c\in Z_p\).

The protocol has three rounds:

\[B\rightarrow A: a=g^s\]

\[A\rightarrow B: c\]

\[B\rightarrow A: r=wc+s\] Alice validates Bob response by checking that
\(g^r=v^ca\). Indeed,

\[g^r={g^w}^c g^s=u^ca\]

**Ex. 2: Diffie-Hellman pairs**

Fix another \(h\) an integer. The powers of \(h\) form a subgroup \(H\)
inside the group \(Z_p\).

-   Public input \(u\in G,v\in H\).
-   Agreed relation
    \(((u,v),w)\in R \Leftrightarrow g^w=u \wedge h^w=v\).
-   Private input \(w\).
-   Bob will need some random \(s\in Z_p\).
-   Alice will need some random \(c\in Z_p\).

The protocol has three rounds:

\[B\rightarrow A: a=g^s\]

\[A\rightarrow B: c\]

\[B\rightarrow A: r=wc+s\] Alice validates Bob response by checking that
\(g^r=u^ca\) and that \(h^r=v^ca\). Indeed,

\[g^r={g^w}^c g^s=u^ca.\] and similarly for \(h\) with \(v\).

**Ex. 3: Cyphertext encryption**

Fix \(h=g^x=PubKT\) an integer.

-   Public input \((m,u,v)\).
-   Agreed relation
    \(((m,u,v),w)\in R \Leftrightarrow u=g^w \wedge v={g^x}^w m  \Leftrightarrow (u,v)=\{m\}_{PubKT}\)
    under [ ElGamal](/wiki/ElGamalSchnorr "wikilink") with ephemeral key
    \(w\).
-   Private input \(w\).
-   Bob will need some random \(s\in Z_p\).
-   Alice will need some random \(c\in Z_p\).

The protocol has three rounds:

\[B\rightarrow A: a=g^s\]

\[A\rightarrow B: c\]

\[B\rightarrow A: r=wc+s\] Alice validates Bob response by checking that
\(g^r=u^ca\) and that \({g^x}^r=(v/m)^ca\). Indeed,

\[g^r={g^w}^c g^s=u^ca.\] and similarly for \(h=g^x\) with \(v'=v/m\).

Composability
-------------

Consider \(v_0, v_1\) and \(R_0, R_1\). Say Bob pretends to have
\(w_0, w_1\) such that \((v_0,w_0)\in R_0 \wedge (v_1,w_1)\in R_1\), and
does not want to disclose them. Is there a Sigma protocol for this new
relation
\(R_0\wedge R_1=\{(v_0,v_1),(w_0,w_1)\,|\,(v_0,w_0)\in R_0 \wedge (v_1,w_1)\in R_1\}\)?
If there was some for \(R_0\) and \(R_1\), then yes. It suffices to
combine the parallel run of both protocols into one, as tuples.

Now, say Bob pretends to have one of \(w_0\) or \(w_1\), and does not
want to disclose it, not tell which one it is. Is there a Sigma protocol
for this new relation
\(R_0\vee R_1=\{(v_0,v_1),(w_0,w_1)\,|\,(v_0,w_0)\in R_0 \vee (v_1,w_1)\in R_1\}\)?
If there was some for \(R_0\) and \(R_1\), then sometimes yes. This
sometimes is related to Bob's ability to simulate, on its own, a valid
run of the component Sigma protocols. In other words, for instance say
that Bob does not know \(w_1\), but that he has the freedom to choose
himself the corresponding challenge \(c_1\). Is he able to efficiently
generate \(a_1,c_1,r_1\) so that they are valid? If so, then yes. This
particular property of the component Sigma protocols is referred to as
"existence of a simulator" or "special honest-verifier zero-knowledge".
Here is how.

-   Public input \(v_0,v_1\).
-   Agreed relation \(R_0\wedge R_1\).
-   Private input \(w_0\), say, but could equally be \(w_1\).
-   Bob will need some random \((u_0,a_0)\in R_0\) and some run
    \(a_1,c_1,r_1\).
-   Alice will need some random \(s\).

The protocol has three rounds:

\[B\rightarrow A: (a_0,a_1)\]

\[A\rightarrow B: s\]

\[B\rightarrow A: (c_0,c_1),(r_0,r_1)\] where \(r_0\) is computed by Bob
thanks to his knowledge of \(w_0\). Alice validates Bob response by
checking that:

-   \(s=c_0\oplus c_1\)
-   \(a_0,c_0,r_0\) is valid
-   \(a_1,c_1,r_1\) is valid

The intuition is that Bob has "divided up" the random challenge \(s\)
into \(c_0\) which is random, and \(c_1\), which is chosen. He has
passed the challenge \(c_0\) thanks to his \(w_0\). He has passed the
challenge \(c_1\) because it was an easy challenge that he has himself
chosen.

In the case of the example of the Discrete Logarithm, there exists a
simulator, so that this can be done. Indeed, to generate \(a_1,c_1,r_1\)
you:

-   Pick \(c_1,r_1\).
-   Let \(a_1=g^{r_1}v_1^{-c_1}\).

Indeed, you then have \(g^{r_1}=v_1^{c_1}a_1\) so that the run is valid.

Non-interactive version
-----------------------

Instead of doing the Sigma protocol in three rounds, we could just do it
in one round, according to the Fiat-Shamir heuristics. The idea is that
the prover challenges himself with something that he does not really
control, namely \(h(v,a)\), where \(h\) is a hash function like SHA2.