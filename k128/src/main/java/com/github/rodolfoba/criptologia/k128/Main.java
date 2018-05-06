package com.github.rodolfoba.criptologia.k128;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import com.github.rodolfoba.criptologia.k128.algoritmo.K128;

public class Main {

    private static final String ARG_MODO_CRIPTOGRAFA = "-c";
    private static final String ARG_MODO_DECRIPTOGRAFA = "-d";
    private static final String ARG_ENTRADA = "-i";
    private static final String ARG_SAIDA = "-o";
    private static final String ARG_SENHA = "-p";
    private static final String ARG_APAGA_ENTRADA = "-a";
    
    public static void main(String[] argsArray) throws Exception {
        List<String> args = Arrays.asList(argsArray);
        if (args.size() < 4) {
            throw new IllegalArgumentException("Parametros invalidos");
        }
        
        // Modo
        boolean isModoCriptografa = args.contains(ARG_MODO_CRIPTOGRAFA);
        boolean isModoDecriptografa = args.contains(ARG_MODO_DECRIPTOGRAFA);
        if ((isModoCriptografa && isModoDecriptografa) || (!isModoCriptografa && !isModoDecriptografa)) {
            throw new IllegalArgumentException("Parametro modo invalido (-c ou -d)");
        }
        
        String pathEntrada = args.get(args.indexOf(ARG_ENTRADA) + 1);
        String pathSaida = args.get(args.indexOf(ARG_SAIDA) + 1);
        String senha = args.get(args.indexOf(ARG_SENHA) + 1);
        boolean isApagar = args.contains(ARG_APAGA_ENTRADA);
        
        Instant inicio = Instant.now();
        byte[] entrada = Files.readAllBytes(Paths.get(pathEntrada));
        byte[] saida;
        if (isModoCriptografa) {
            saida = K128.criptografar(entrada, senha);
        } else {
            saida = K128.decriptografar(entrada, senha);
        }
        Instant fim = Instant.now();
        Files.write(Paths.get(pathSaida), saida);
        
        System.out.println("Tempo (ms): " + Duration.between(inicio, fim).toMillis());
    }
}
