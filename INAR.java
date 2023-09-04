package Collections;

import java.util.*;

public class INAR {

    public static class NoArvore {

        private EstadoQuebraCabeca estado;
        private NoArvore pai;
        private NoArvore filhoEsquerda;
        private NoArvore filhoDireita;

        // Construtor da classe NoArvore
        public NoArvore(EstadoQuebraCabeca estado, NoArvore pai) {
            this.estado = estado;
            this.pai = pai;
            this.filhoEsquerda = null;
            this.filhoDireita = null;
        }

        // Metodos de acesso para obter informacoes sobre o estado do no
        public EstadoQuebraCabeca getEstado() {
            return estado;
        }

        public NoArvore getPai() {
            return pai;
        }

        public NoArvore getFilhoEsquerda() {
            return filhoEsquerda;
        }

        public NoArvore getFilhoDireita() {
            return filhoDireita;
        }

        public void setFilhoEsquerda(NoArvore filhoEsquerda) {
            this.filhoEsquerda = filhoEsquerda;
        }

        public void setFilhoDireita(NoArvore filhoDireita) {
            this.filhoDireita = filhoDireita;
        }
    }

    private static class EstadoQuebraCabeca {

        private final int[][] quebraCabeca;
        private final int linhaVazia;
        private final int colunaVazia;

        // Construtor da classe EstadoQuebraCabeca
        public EstadoQuebraCabeca(int[][] quebraCabeca, int linhaVazia, int colunaVazia) {
            this.quebraCabeca = quebraCabeca;
            this.linhaVazia = linhaVazia;
            this.colunaVazia = colunaVazia;
        }

        // Metodos de acesso para obter informacoes sobre o estado do quebra-cabeca
        public int[][] getQuebraCabeca() {
            return quebraCabeca;
        }

        public int getLinhaVazia() {
            return linhaVazia;
        }

        public int getColunaVazia() {
            return colunaVazia;
        }

        // Sobrescrevendo os metodos equals() e hashCode() para comparar estados
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            EstadoQuebraCabeca outro = (EstadoQuebraCabeca) obj;
            return Arrays.deepEquals(this.quebraCabeca, outro.getQuebraCabeca());
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(quebraCabeca);
        }
    }

    // Verifica se o estado do quebra-cabeca e o estado objetivo
    public static boolean ehEstadoObjetivo(EstadoQuebraCabeca estado) {
        int[][] quebraCabeca = estado.getQuebraCabeca();
        int contador = 1;
        for (int[] linha : quebraCabeca) {
            for (int num : linha) {
                if (contador == 9) {
                    contador = 0;
                }
                if (num != contador) {
                    return false;
                }
                contador++;
            }
        }
        return true;
    }

    // Gera os estados sucessores a partir do estado atual do quebra-cabeca
    public static List<EstadoQuebraCabeca> gerarEstadosSucessores(EstadoQuebraCabeca estado) {
        List<EstadoQuebraCabeca> sucessores = new ArrayList<>();
        int[][] quebraCabeca = estado.getQuebraCabeca();
        int linhaVazia = estado.getLinhaVazia();
        int colunaVazia = estado.getColunaVazia();

        int[][] movimentos = {
            {-1, 0}, // Movimento para cima
            {1, 0}, // Movimento para baixo
            {0, -1}, // Movimento para a esquerda
            {0, 1} // Movimento para a direita
        };

        for (int[] movimento : movimentos) {
            int novaLinha = linhaVazia + movimento[0];
            int novaColuna = colunaVazia + movimento[1];

            if (novaLinha >= 0 && novaLinha < quebraCabeca.length && novaColuna >= 0 && novaColuna < quebraCabeca[0].length) {
                int[][] novoQuebraCabeca = Arrays.stream(quebraCabeca).map(int[]::clone).toArray(int[][]::new);
                novoQuebraCabeca[linhaVazia][colunaVazia] = novoQuebraCabeca[novaLinha][novaColuna];
                novoQuebraCabeca[novaLinha][novaColuna] = 0;

                sucessores.add(new EstadoQuebraCabeca(novoQuebraCabeca, novaLinha, novaColuna));
            }
        }

        return sucessores;
    }

    // Verifica se um quebra-cabeca e solucionavel
    public static boolean ehSolucionavel(int[][] quebraCabeca) {
        int[] quebraCabecaPlano = new int[9];
        int indice = 0;
        int contadorInversoes = 0;

        for (int[] linha : quebraCabeca) {
            for (int num : linha) {
                quebraCabecaPlano[indice++] = num;
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 9; j++) {
                if (quebraCabecaPlano[i] != 0 && quebraCabecaPlano[j] != 0 && quebraCabecaPlano[i] > quebraCabecaPlano[j]) {
                    contadorInversoes++;
                }
            }
        }

        return contadorInversoes % 2 == 0;
    }

    // Imprime o estado do quebra-cabeca
    public static void imprimirEstadoQuebraCabeca(int[][] quebraCabeca) {
        for (int[] linha : quebraCabeca) {
            for (int num : linha) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Imprime a solucao encontrada
    public static void imprimirSolucao(NoArvore noSolucao) {
        List<EstadoQuebraCabeca> caminhoSolucao = new ArrayList<>();
        NoArvore noAtual = noSolucao;

        while (noAtual != null) {
            caminhoSolucao.add(noAtual.getEstado());
            noAtual = noAtual.getPai();
        }

        Collections.reverse(caminhoSolucao);

        System.out.println("Solucao:");
        for (EstadoQuebraCabeca estado : caminhoSolucao) {
            imprimirEstadoQuebraCabeca(estado.getQuebraCabeca());
        }
    }

    public static void main(String[] args) {
        int[][] quebraCabecaInicial = {
            {1, 7, 8},
            {0, 3, 6},
            {4, 5, 2}
        };

        if (!ehSolucionavel(quebraCabecaInicial)) {
            System.out.println("A configuracao inicial nao tem solucao.");
            return;
        }

        // Encontrar a posicao da peca vazia no quebra-cabeca inicial
        int linhaVazia = 0;
        int colunaVazia = 0;
        outerloop:
        for (int linha = 0; linha < quebraCabecaInicial.length; linha++) {
            for (int coluna = 0; coluna < quebraCabecaInicial[linha].length; coluna++) {
                if (quebraCabecaInicial[linha][coluna] == 0) {
                    linhaVazia = linha;
                    colunaVazia = coluna;
                    break outerloop;
                }
            }
        }

        // Criar o estado inicial
        EstadoQuebraCabeca estadoInicial = new EstadoQuebraCabeca(quebraCabecaInicial, linhaVazia, colunaVazia);

        // Executar busca em arvore binaria para encontrar a solucao
        NoArvore raiz = new NoArvore(estadoInicial, null);
        Queue<NoArvore> fila = new LinkedList<>();
        Set<EstadoQuebraCabeca> visitados = new HashSet<>();

        fila.add(raiz);

        while (!fila.isEmpty()) {
            NoArvore noAtual = fila.poll();
            EstadoQuebraCabeca estadoAtual = noAtual.getEstado();

            if (ehEstadoObjetivo(estadoAtual)) {
                // Encontrou a solução
                imprimirSolucao(noAtual);
                break;
            }

            List<EstadoQuebraCabeca> sucessores = gerarEstadosSucessores(estadoAtual);
            for (EstadoQuebraCabeca sucessor : sucessores) {
                if (!visitados.contains(sucessor)) {
                    visitados.add(sucessor);
                    NoArvore noFilho = new NoArvore(sucessor, noAtual);

                    if (noAtual.getFilhoEsquerda() == null) {
                        noAtual.setFilhoEsquerda(noFilho);
                    } else if (noAtual.getFilhoDireita() == null) {
                        noAtual.setFilhoDireita(noFilho);
                    }

                    fila.add(noFilho);
                }
            }
        }
    }
}
