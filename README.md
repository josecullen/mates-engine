# mates engine endpoint
Mates engine endpoint consiste en un conjunto de servicios REST para obtener distintos tipos de problemas matemáticos. Actualmente, el motor tiene los siguientes tipos de problemas con sus respectivos enpoints. 

* *simple-problem*: problemas de aritmética. Con sencillos, nos referimos a "clásicos", es decir, que implique operaciones del tipo `suma` `resta` `multiplicación` `división`. En cuanto a la complejidad de dichas operaciones, se las puede tener tan complejas como se desee: `(a + b) * (c * d) / (f / g) ...` 
* *equation-problem*: problemas de ecuaciones de grado 1, 2 ó 3.
* *system-equation-problem*: sistema de ecuaciones de 1, 2, ó 3 grados.
* *simple-module-problem*: es un problema de módulo, del tipo `3 % 23 = ?` 
