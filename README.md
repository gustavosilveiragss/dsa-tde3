## Alunos
- Gustavo Silveira e Silva
- Vinicius de Andrade Deolindo
  
## Introdução  
  
Este documento diz respeito ao TDE 03 da disciplina de Resolução de Problemas Estruturados em Computação. A proposta para o projeto iniciava solicitando que a **dupla** escolhesse:  
  
```txt  
- 1 algoritmo do grupo A (Insert Sort, Selection Sort, Comb Sort, Bogo Sort)  
- 2 algoritmos do grupo B (Merge Sort , Quick Sort, Shell Sort, Heap Sort)  
- 1 algoritmo do grupo C (Radix sort, Gnome Sort, Counting sort, Bucket sort, Cocktail sort , Timsort, Stooge Sort)  
```  
  
Assim, a **dupla** escolheu:  
  
```txt  
- Grupo A - Bogo Sort  
- Grupo B - Merge Sort, Shell Sort  
- Grupo C - Cocktail Sort  
```  
  
Para esse grupo de algoritmos, devem ser realizados **testes com vetores de inteiros**, sendo necessários testes com os tamanhos de **1.000, 10.000, 100.000 e 1.000.000** para, depois de **5 rodadas de teste para cada quantidade**, ser apresentada uma média. Assim, poderemos realizar um **relatório comparativo dos algoritmos**.  
  
Por último, existe a seguinte limitação: "Só será permitida a utilização de vetores, estruturas de nó, tipos primitivos (int, float, boolean), String, estruturas de matrizes (não a função pronta), random, seeds, formas para ler os dados como buffer e scanner e bibliotecas de representação visual e exportação dos dados para análise e construção dos gráficos. Os gráficos podem ser feitos em qualquer ferramenta."  

Para o Bogo Sort, a equipe optou por incluir apenas testes para vetores de até 50 números. Por consistir de tentativas aleatórias de sequências, seria inviável esperar pelo resultado com um milhão de elementos. Para compensar, incluímos o algoritmo de Insertion Sort. Também é útil para comparação com o Shell Sort, que usa de um Insertion Sort em uma das etapas. 

Quanto ao Cocktail Sort, foi perceptível a má performance, por isso optamos por incluir o Bubble Sort para comparação. Já que o Cocktail Sort é apenas um Bubble Sort bidirecional, a comparação é interessante.
  
## A1. Bogo Sort  

### A1.1 Explicação  
  
O Bogo Sort é um algoritmo de ordenação extremamente ineficiente. Seu funcionamento básico está em permutações aleatórias de um vetor até que ele esteja ordenado. Após cada permutação, o algoritmo precisa verificar a ordenação. É interessante notar que, dado um número ilimitado de tentativas, o algoritmo acertar a ordem na primeira permutação.   

### A1.2 Resultados  

Por sua natureza imprevisível, o Bogo Sort se mostrou inviável para testes. O que decidimos fazer foi apenas incluir um teste com 10 elementos, demonstrando a instabilidade, com 5 rodadas com resultados completamente diferentes.

![bogosort](https://github.com/gustavosilveiragss/dsa-tde3/blob/master/analysis/bogosort.png?raw=true)

## B1 Merge Sort 

### B1.1 Explicação  

O Merge Sort é um algoritmo de ordenação eficiente. Seu funcionamento básico está em dividir o vetor na metade recursivamente até ter arrays unitários, que são naturalmente ordenados. Em seguida, o algoritmo combina os "sub-vetores" de forma ordenada (por isso o nome "merge"), construindo vetores maiores, até que todo o vetor original esteja ordenado. Essa estratégia é comum, e normalmente apelidada de divisão e conquista.

### B1.2 Resultados  

O Merge Sort apresentou ótimos resultados consistentemente

![mergesort](https://github.com/gustavosilveiragss/dsa-tde3/blob/master/analysis/mergesort.png?raw=true)

## B2 Shell Sort 
### B2.1 Explicação  

O Shell Sort usa como base o Insertion Sort. O algoritmo funciona dividindo o vetor em grupos menores, que são ordenados usando o Insertion Sort. Inicialmente, os elementos distantes são comparados e trocados se necessário. O tamanho entre comparações é gradualmente reduzido até chegar a 1, onde o algoritmo se comporta como um Insertion Sort comum. O interessante é que podem ser usadas várias sequências de distâncias diferentes. Uma das sequências mais comuns é a de autoria de Donald Knuth, definida por:

$$\frac{3^k-1}{2} \text{ , não maior que } [\frac{N}{3}]$$

### B2.2 Resultados

O Shell Sort também apresentou resultados ótimos, sendo até melhores que o Merge Sort para certas quantidades.

![shellsort](https://github.com/gustavosilveiragss/dsa-tde3/blob/master/analysis/shellsort.png?raw=true)

## C1 Cocktail Sort  
### C1.1 Explicação

O Cocktail Sort, também conhecido como Bidirectional Bubble Sort, é uma variação do Bubble Sort. Ele funciona por percorrer o vetor em ambas as direções em cada iteração. Por ir passando os elementos maiores para o fim e menores para o começo em cada iteração, pode fazer menos comparações, evitando os primeiros e últimos elementos. Assim, possui performance superior ao Bubble Sort convencional.

### C1.2 Resultados

Já o Cocktail Sort apresentou uma performance ruim, com os resultados de 100k tendo próximo dos 8 segundos de execução, e 1 milhão, 12 minutos.

![cocktailsort](https://github.com/gustavosilveiragss/dsa-tde3/blob/master/analysis/cocktailsort.png?raw=true)

## A2 Insertion Sort
### A2.1 Explicação

O Insertion Sort é um algoritmo simples que funciona construindo o vetor ordenado um elemento por vez, sempre mantendo a porção já verificada do vetor em ordem. Para cada iteração, um elemento é comparado aos anteriores, já ordenados. Em seguida, move os maiores uma posição para frente, e insere o elemento naquela posição.

### A2.1 Resultados

O Insertion Sort apresentou resultados relativamente bons, comparado à outros implementados. Dado sua simplicidade, é bastante positivo

![insertionsort](https://github.com/gustavosilveiragss/dsa-tde3/blob/master/analysis/insertionsort.png?raw=true)

## Bubble Sort
### Explicação

O Bubble Sort é um algoritmo que faz com que os maiores valores "borbulhem" gradualmente para o final do vetor. A cada iteração, compara pares de elementos consecutivos e os troca se estiverem fora de ordem. O processo é repetido até que o maior elemento esteja no final. Quando não forem mais necessárias trocas, o vetor está ordenado.

### Resultados

Dos algoritmos implementados pela equipe, apresentou a pior performance, com 100k levando 12 segundos e 1 milhão, 20 minutos

![bubblesort](https://github.com/gustavosilveiragss/dsa-tde3/blob/master/analysis/bubblesort.png?raw=true)

## Comparativos Finais

Pela similaridade dos algoritmos Shell e Insertion Sort, fizemos também uma comparação entre os dois:

![shell_insertion](https://github.com/gustavosilveiragss/dsa-tde3/blob/master/analysis/shell_insertion.png?raw=true)

Igualmente, comparamos o Cocktail e Bubble Sort:

![cocktail_bubble](https://github.com/gustavosilveiragss/dsa-tde3/blob/master/analysis/cocktail_bubble.png?raw=true)

Para análise final, comparamos todos os algoritmos, entre diferentes quantidades. Podemos concluir que, dadas as condições dos algoritmos (Shell usando sequência de Knuth) e os tamanhos especificados, os algoritmos Shell e Merge Sort têm melhor performance. Sendo que na maioria dos casos o Merge Sort é mais veloz.

![all](https://github.com/gustavosilveiragss/dsa-tde3/blob/master/analysis/all.png?raw=true)
