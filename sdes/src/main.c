/*
 ============================================================================
 Name        : main.c
 Author      : rodolfoba
 Version     :
 Copyright   :
 Description : Simplified DES (S-DES) algorithm implementation in C
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include "bitarray/bit_array.h"
#include "sdes.h"

int main(void) {
	puts("Simplified DES (S-DES)");
	//puts("Você deseja Criptografar(c) ou Decriptografar(d)?: ");
	//puts("Digite a mensagem de entrada (bloco binário de 8 bits): ");
	//puts("Digite a chave (binário de 10 bits): ");

	BIT_ARRAY* chave = bit_array_create(10);
	BIT_ARRAY* entrada = bit_array_create(8);
	BIT_ARRAY* criptografada = bit_array_create(8);
	BIT_ARRAY* descriptografada = bit_array_create(8);

	bit_array_from_str(chave, "1010000010");
	bit_array_from_str(entrada, "11010010");

	char entradaTexto[bit_array_length(entrada)];
	printf("entrada=%s\n", bit_array_to_str(entrada, entradaTexto));

	char chaveTexto[bit_array_length(chave)];
	printf("chave=%s\n", bit_array_to_str(chave, chaveTexto));

	criptografada = criptografar(entrada, chave);
	char criptografadaTexto[bit_array_length(criptografada)];
	printf("criptografada=%s\n", bit_array_to_str(criptografada, entradaTexto));

	descriptografada = descriptografar(criptografada, chave);
	char descriptografadaTexto[bit_array_length(descriptografada)];
	printf("descriptografada=%s\n", bit_array_to_str(descriptografada, entradaTexto));

	puts("Fim.");
	return EXIT_SUCCESS;
}
