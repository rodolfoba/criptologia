package com.github.rodolfoba.criptologia.k128;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import com.github.rodolfoba.criptologia.k128.algoritmo.K128;

public class Main {

    private static final String ARG_MODO_CRIPTOGRAFA = "-c";
    private static final String ARG_MODO_DECRIPTOGRAFA = "-d";
    private static final String ARG_MODO_ALEATORIEDADE_1 = "-1";
    private static final String ARG_MODO_ALEATORIEDADE_2 = "-2";
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
        boolean isModoAleatoriedade1 = args.contains(ARG_MODO_ALEATORIEDADE_1);
        boolean isModoAleatoriedade2 = args.contains(ARG_MODO_ALEATORIEDADE_2);
        
        String pathEntrada = args.get(args.indexOf(ARG_ENTRADA) + 1);
        String pathSaida = args.get(args.indexOf(ARG_SAIDA) + 1);
        String senha = args.get(args.indexOf(ARG_SENHA) + 1);
        boolean isApagar = args.contains(ARG_APAGA_ENTRADA);
        
        Instant inicio = Instant.now();
        byte[] entrada = Files.readAllBytes(Paths.get(pathEntrada));
        
        if (isModoCriptografa) {
            byte[] saida = K128.criptografar(entrada, senha);
            Files.write(Paths.get(pathSaida), saida);
        } else if(isModoDecriptografa) {
            byte[] saida = K128.decriptografar(entrada, senha);
            Files.write(Paths.get(pathSaida), saida);
        } else if (isModoAleatoriedade1) {
            K128.calcularAleatoriedadeMetodo1(entrada, senha);
        } else if(isModoAleatoriedade2) {
            K128.calcularAleatoriedadeMetodo2(entrada, senha);
        } else {
            throw new IllegalArgumentException("Modo inválido");
        }
        
        Instant fim = Instant.now();
        
        if (isApagar) {
            int size = (int) Files.size(Paths.get(pathEntrada));
            byte[] bytes = new byte[size];
            for (int i = 0; i < size; i++) {
                bytes[i] = 0x20; 
            }
            System.out.println("Zerando e apagando arquivo...");
            Files.write(Paths.get(pathEntrada), bytes, StandardOpenOption.TRUNCATE_EXISTING);
            Files.deleteIfExists(Paths.get(pathEntrada));
        }
        
        System.out.println("Tempo (ms): " + Duration.between(inicio, fim).toMillis());
    }
}
