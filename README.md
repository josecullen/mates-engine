# mates engine endpoint
Mates engine endpoint consiste en un conjunto de servicios REST para obtener distintos tipos de problemas matemáticos. Actualmente, el motor tiene los siguientes tipos de problemas con sus respectivos enpoints. 

* *simple-problem*: problemas de aritmética. Con sencillos, nos referimos a "clásicos", es decir, que implique operaciones del tipo `suma` `resta` `multiplicación` `división`. En cuanto a la complejidad de dichas operaciones, se las puede tener tan complejas como se desee: `(a + b) * (c * d) / (f / g) ...` 
* *equation-problem*: problemas de ecuaciones de grado 1, 2 ó 3.
* *system-equation-problem*: sistema de ecuaciones de 1, 2, ó 3 grados.
* *simple-module-problem*: es un problema de módulo, del tipo `3 % 23 = ?` 

## Modelos de datos 
Los servicios REST son del tipo POST y precisan que se envíen en el *body* los datos de configuración adecuados, en formato JSON. Más adelante en este README describiremos cómo hacer cada llamada, pero ahora listaremos los modelos de datos generales para dichas llamadas. 


### Datos Simples
#### expression
Describe la forma de la expresión matemática. Las variables se pueden repetir y los operadores también, ya que no son los que el problema utilizará (estos se definen mediante otros parámetros).
```json
"expression": string ["(a + b) [+ (c + d) . . .]"]
```


#### probablySign
La probabilidad de que la variable sea negativa. `0` indica que nunca será negativa; `1` indica que siempre será negativa.
```json
"probablySign": double [0 - 1]
```

#### operationPatern
Indica los operadores que pueden llegar a aparecer en el problema. 
```json
"operationsPattern": string ["+-/*\\^"] ["and|eq|then|or"]
```

#### divisionFactor
La divisibilidad de los operandos. Este factor trabaja con los divisores del número indicado.
```json
"divisionFactor": int [0 - inf]
```
por ejemplo: si tenemos el factor de división `1`, sabemos que sólo `1` divide a `1`. Por lo tanto tendremos sólo números enteros. 
Si el factor de división es `12`, los divisores de `12` son `1, 2, 3, 4, 6, 12`. Por lo tanto pueden aparecer números divididos por ese conjunto de números, como `[1, 1/2, 1/3, 1/4, 1/6, 1/12, 2, 2/3, 3, 3/2, ...]`

#### max y min
Limita los valores máximos y mínimos de una variable. 
```json
max: int [0 - inf]
min: int [0 - inf]
```

#### equationLevel
Especifica el nivel de la ecuación
```json
"equationLevel": string ["LEVEL_1","LEVEL_2","LEVEL_3"],
```
 
### Datos Compuestos
#### operationConfig
Configuración de las operaciones de un problema. En este caso, la probabilidad de signo se refiere a la probabilidad de que sea negativa la operación completa (no los operandos que la componen), es decir `-(a + b)`
```json
"operationConfig":{
    "probablySign": probablySign,
    "operationsPattern": operationPattern
}
```

#### aritmeticVariableConfig
La configuración de las variables.

```json
"aritmeticVariableConfig": {
    "max": max,
    "min": min,
    "probablySign": probablySign,
    "divisionFactor":divisionFactor
}
```

## Endpoints
A continuación pasaremos a enumerar los endpoints de la aplicación y los datos de entrada que precisan. 

### simple-problem `/v1/aritmetic/simple-problem`
#### body
```json
{
    "expression": expression,
    "operationConfig":operationConfig,
    "aritmeticVariableConfig": aritmeticVariableConfig
}
```

### equation-problem `/v1/aritmetic/equation-problem`
#### body
```json
{
    "equationLevel": equationLevel,
    "a": aritmeticVariableConfig,
    "x": aritmeticVariableConfig,
    "x2": aritmeticVariableConfig,
    "x3": aritmeticVariableConfig
}
```

### system-equation-problem `/v1/aritmetic/system-equation-problem`
#### body
```json
{
    "equationLevel": equationLevel,
    "x": aritmeticVariableConfig,
    "constant": aritmeticVariableConfig,
    "operationConfig": operationConfig
}
```

### simple-module-problem `/v1/aritmetic/simple-module-problem`
#### body
```json
{
    "a": aritmeticVariableConfig,
    "pow": aritmeticVariableConfig,
    "mod": aritmeticVariableConfig
}
```

### logic-problem `/v1/aritmetic/logic-problem`
#### body
```json
{
    "expression": expression,
    "probablySign": probablySign,
    "operations": operations ["and|or|then|eq"]
}
```













