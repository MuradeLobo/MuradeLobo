#include <stdio.h>
#include <stdlib.h>

// Definição da estrutura de um nó da árvore
typedef struct No {
    int dado;
    struct No* esquerda;
    struct No* direita;
} No;

// Definição da estrutura de uma pilha
typedef struct Pilha {
    No** array;
    int tamanho;
    int capacidade;
} Pilha;

// Função para criar um novo nó
No* criarNo(int dado, No* esquerda, No* direita) {
    No* novoNo = (No*)malloc(sizeof(No));
    novoNo->dado = dado;
    novoNo->esquerda = esquerda;
    novoNo->direita = direita;
    return novoNo;
}

// Função para criar uma nova pilha
Pilha* criarPilha(int capacidade) {
    Pilha* pilha = (Pilha*)malloc(sizeof(Pilha));
    pilha->array = (No**)malloc(capacidade * sizeof(No*));
    pilha->tamanho = 0;
    pilha->capacidade = capacidade;
    return pilha;
}

// Função para empilhar um nó na pilha
void empilhar(Pilha* pilha, No* no) {
    if (pilha->tamanho >= pilha->capacidade) {
        printf("Pilha cheia\n");
        return;
    }
    pilha->array[pilha->tamanho++] = no;
}

// Função para desempilhar um nó da pilha
No* desempilhar(Pilha* pilha) {
    if (pilha->tamanho <= 0) {
        printf("Pilha vazia\n");
        return NULL;
    }
    return pilha->array[--pilha->tamanho];
}

// Função para verificar se a pilha está vazia
int pilhaVazia(Pilha* pilha) {
    return pilha->tamanho == 0;
}

// Função para mostrar o caminho contido na pilha
void mostrarCaminho(Pilha* pilha) {
    for (int i = 0; i < pilha->tamanho; ++i) {
        printf("%d ", pilha->array[i]->dado);
    }
    printf("\n");
}

// Função de busca em profundidade com caminho, recursiva
void buscaProfundidadeComCaminhoRec(No* no, int valor, Pilha* caminho, int* numCaminhos) {
    if (no == NULL) {
        return;
    }

    empilhar(caminho, no);

    if (no->dado == valor) {
        (*numCaminhos)++;
        printf("Caminho %d: ", *numCaminhos);
        mostrarCaminho(caminho);
    } else {
        buscaProfundidadeComCaminhoRec(no->esquerda, valor, caminho, numCaminhos);
        buscaProfundidadeComCaminhoRec(no->direita, valor, caminho, numCaminhos);
    }

    desempilhar(caminho);
}

// Função de busca em profundidade com caminho
void buscaProfundidadeComCaminho(No* raiz, int valor, int* numCaminhos) {
    Pilha* caminho = criarPilha(100);
    buscaProfundidadeComCaminhoRec(raiz, valor, caminho, numCaminhos);
}

int main() {
    No* no20 = criarNo(3, NULL, NULL);
    No* no19 = criarNo(10, NULL, NULL);
    No* no18 = criarNo(8, NULL, NULL);
    No* no17 = criarNo(17, NULL, NULL);
    No* no16 = criarNo(12, NULL, NULL);
    No* no15 = criarNo(2, NULL, NULL);
    No* no14 = criarNo(1, NULL, NULL);
    No* no13 = criarNo(23, no19, no20);
    No* no12 = criarNo(2, no17, no18);
    No* no11 = criarNo(12, no15, no16);
    No* no10 = criarNo(3, no13, no14);
    No* no9 = criarNo(34, no11, no12);
    No* no8 = criarNo(6, no9, no10);
    No* no7 = criarNo(5, NULL, NULL);
    No* no6 = criarNo(9, NULL, NULL);
    No* no5 = criarNo(11, NULL, NULL);
    No* no4 = criarNo(2, no7, no8);
    No* no3 = criarNo(3, no5, no6);
    No* no2 = criarNo(4, no3, no4);
    No* no1 = criarNo(1, no2, NULL);

    int numCaminhos = 0;
    buscaProfundidadeComCaminho(no1, 10, &numCaminhos);

    return 0;
}

