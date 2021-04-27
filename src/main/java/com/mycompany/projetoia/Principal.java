
package com.mycompany.projetoia;

public class Principal {
    private static double[] populacao;
    private static String[] binarios;
    private static int[] decimais;
    private static double[] fit;
    private static double probabilidadeDCruzar = 0.7;
    private static double probabilidadeDMutacao = 0.01;
    private static int individuos = 200;
    private static int genes = 8;
    
    public static void main(String[] args) {
        generarpopulacaoInicial();
        fit = new double[individuos];
        for (int j = 0; j < fit.length; j++){ fit[j] = fit(decimais[j]); }
        for (int i = 0; i < genes*individuos; i++) {
            int pai = paiMenor();
            int mama = maeMenor(pai);
            cruza(pai,mama);
            mutacao(pai,mama);
            for (int j = 0; j < individuos; j++) decimais[j] = binarioToDecimal(binarios[j]);
            for (int j = 0; j < fit.length; j++){ fit[j] = fit(decimais[j]); }
        }
        System.out.println("f(x) = "+fit(decimais[paiMaior()]));
        System.out.println("x = "+decimais[paiMaior()]);        
    }
    
    public static void generarpopulacaoInicial() {
        int espacos = individuos*genes, cont = 0;
        populacao = new double[espacos];
        decimais = new int[individuos];
        binarios = new String[individuos];
        for (int i = 0; i < espacos; i++) {
            populacao[i] = Math.random();
        }
        
        for (int i = 0; i < individuos; i++) {
            String binario = "";
            for (int j = 0; j < genes; j++) {
                binario += (int) Math.round(populacao[cont]);
                cont ++;
                binarios[i] = binario;
            }
            decimais[i] = binarioToDecimal(binario);
        }
    }
    
    public static int binarioToDecimal(String binario) {
        char[] correntee = binario.toCharArray();
        char[] corrente = new char[correntee.length];
        for (int i = 0; i < correntee.length; i++) corrente[correntee.length-i-1] = correntee[i];
        int valor = 0, resultado = 0, aux = 0;
        for (int i = 0; i < corrente.length; i++) {
            valor = aux * 2;
            if (valor == 0) valor = 1;
            if (corrente[i] == '1') {
                resultado += valor;
            }
            aux = valor;
        }
        return resultado;
    }
    
    public static double fit(int x) {return (x*x)-(4*x)+6;}
    
    public static int paiMaior() {
        double maior = fit[0];
        int posicao = 0;
        for (int i = 1; i < fit.length; i++) {
            if (fit[i] > maior){
                maior = fit[i];
                posicao = i;
            }
        }
        return posicao;
    }
    
    public static int maeMaior(double pai) {
        double maior = 0; int posicao = 0;
        if (pai == 0) maior = fit[1];
        else maior = fit[0];
        for (int i = 0; i < fit.length; i++) {
            if (fit[i] > maior && i != pai) {
                maior = fit[i];
                posicao = i;
            }
        }
        return posicao;
    }
    
    public static int paiMenor() {
        double menor = fit[0];
        int posicao = 0;
        for (int i = 1; i < fit.length; i++) {
            if (fit[i] < menor){
                menor = fit[i];
                posicao = i;
            }
        }
        return posicao;
    }
    
    public static int maeMenor(double pai) {
        double menor = 0; int posicao = 0;
        if (pai == 0) menor = fit[1];
        else menor = fit[0];
        for (int i = 0; i < fit.length; i++) {
            if (fit[i] < menor && i != pai) {
                menor = fit[i];
                posicao = i;
            }
        }
        return posicao;
    }
    
    public static void cruza(int posicaoPai, int posicaoMae) {
        if (Math.random() < probabilidadeDCruzar) {
            char[] binarioPai = binarios[posicaoPai].toCharArray();
            char[] binarioMae = binarios[posicaoMae].toCharArray();
            String corrente = "";
            int cruza = Math.round(binarioPai.length/2);
            for (int i = 0; i < cruza; i++) corrente += binarioPai[i];
            cruza = binarioMae.length - cruza;
            for (int i = cruza; i < binarioMae.length; i++) corrente += binarioMae[i];
            binarios[0] = corrente;
        }

    }
    
    public static void mutacao(int posicaoPai, int posicaoMae) {
        if (Math.random() < probabilidadeDMutacao) {           
            char[] binarioPai = binarios[posicaoPai].toCharArray();
            int a = (binarioPai.length) -1;
            int posicao = (int) Math.round(Math.random() * a);
            if (binarioPai[posicao] == '0') binarioPai[posicao] = '1'; else binarioPai[posicao] = '0';
            binarios[1] = String.valueOf(binarioPai);           
            }
        if (Math.random() < probabilidadeDMutacao) {           
            char[] binarioMae = binarios[posicaoMae].toCharArray();
            int a = (binarioMae.length) -1;
            int posicao = (int) Math.round(Math.random() * a);
            if (binarioMae[posicao] == '0') binarioMae[posicao] = '1'; else binarioMae[posicao] = '0';
            binarios[2] = String.valueOf(binarioMae);         
            
        }
        
    }
}
