package br.unicap.ed2.pv;



public class ArvorePV  {
    protected NoPV raiz;

    // public ArvorePV() {
    // this.raiz = null;
    // }

    // @Override
    // public NoPV novoNode(int key) {
    // Altera
    // return new NoPV(key);
    // }
    public boolean ehRaiz(int i) {
        if (raiz == null) {
            return false;
        }
        return raiz.getChave() == i;
    }

    public void inserir(int key) {
        NoPV n = new NoPV(key);
        inserirNoPv(this.raiz, n);
        // Altera
    }

    public NoPV procurar(int key) {
        return procurarPV(raiz, key);
    }

    public void inserirNoPv(NoPV no, NoPV chave) {
        if (raiz == null) {

            raiz = chave;
            raiz.setPai(this.raiz);
            primeiroCaso(this.raiz);

        } else if (chave.getChave() < no.getChave()) {

            if (no.getEsquerda() != null) {
                inserirNoPv(no.getEsquerda(), chave);
            } else {

                no.setEsquerda(chave);
                no.getEsquerda().setPai(no);
                primeiroCaso(no.getEsquerda());
            }
        } else if (chave.getChave() > no.getChave()) {
            if (no.getDireita() != null) {
                inserirNoPv(no.getDireita(), chave);
            } else {
                no.setDireita(chave);
                no.getDireita().setPai(no);
                primeiroCaso(no.getDireita());
            }
        }
    }

    private void primeiroCaso(NoPV no) {
        if (no == raiz) {
            no.setCor(false);
        } else {
            segundoCaso(no);
        }
    }

    private void segundoCaso(NoPV no) {
        if (!(no.getPai().getCor())) {
            // Se o pai for preto não faz nada
        } else {
            terceiroCaso(no);
        }

    }

    private void terceiroCaso(NoPV no) {
        if (tio(no) != null && tio(no).getCor()) { // tio vermelho e diferente de null
            tio(no).setCor(false);
            no.getPai().setCor(false); //pai = preto
            no.getPai().getPai().setCor(true); //avo = vermelho
            primeiroCaso(no.getPai().getPai()); // Volta para o primeiro caso passando o avô, o qual ficará desbalanceado
        } else {
            quartoCaso(no);
        }
    }

    private void quartoCaso(NoPV no) {
        if (no.getPai().getDireita() == no && no.getPai().getPai().getEsquerda() == no.getPai()) {
            rotacaoRR(no);
        } else if (no.getPai().getEsquerda() == no && no.getPai().getPai().getEsquerda() == no.getPai()) {
            rotacaoLL(no);
        }
        quintoCaso(no);
    }

    private void quintoCaso(NoPV no) {
        
        no.setCor(false);
        no.getPai().setCor(true); // pai = vermelho

        if (no.getPai().getEsquerda() == no) {
            if (no.getPai().getDireita() != null) {
                no.getPai().getDireita().setCor(false);
            }
            rotacaoLL(no);
        } else {
            if (no.getPai().getEsquerda() != null) {
                no.getPai().getEsquerda().setCor(false);

            }
            rotacaoRR(no);
        }
    }

    private NoPV tio(NoPV no) { // Função para retornar o tio do no instanciado
        NoPV pai = no.getPai();
        NoPV avo = no.getPai().getPai();
        NoPV tio = null;

        if (avo.getEsquerda() != null && avo.getEsquerda() != pai) {
            tio = avo.getEsquerda();
        } else if (avo.getDireita() != null && avo.getDireita() != pai) {
            tio = avo.getDireita();
        }

        return tio;
    }

    private NoPV procurarPV(NoPV no, int chave) {

        if (no == null) {
            return null;
        }
        if (chave < no.getChave()) {
            return procurarPV(no.getEsquerda(), chave);
        } else if (chave > no.getChave()) {
            return procurarPV(no.getDireita(), chave);
        } else {
            return no;
        }
    }
    
    private void rotacaoRR(NoPV no) {
        NoPV pai = no.getPai();
        NoPV avo = no.getPai().getPai();
        if (pai == avo.getEsquerda()) {
            avo.setEsquerda(no);
        } else {
            avo.setDireita(no);
        }

        pai.setDireita(no.getEsquerda());

        if (no.getEsquerda() != null) {
            no.getEsquerda().setPai(pai);
        }
        no.setEsquerda(pai);
        no.setPai(avo);
        pai.setPai(no);
    }

    private void rotacaoLL(NoPV no) {
        NoPV pai = no.getPai();
        NoPV avo = no.getPai().getPai();
        if (pai == avo.getEsquerda()) {
            avo.setEsquerda(no);
        } else {
            avo.setDireita(no);
        }
        pai.setEsquerda(no.getDireita());
        if (no.getDireita() != null) {
            no.getDireita().setPai(pai);
        }
        no.setDireita(pai);
        no.setPai(avo);
        no.getPai().setPai(no);
    }
    
}