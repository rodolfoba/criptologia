#include <stdio.h>
#include <stdlib.h>
#include "sdes.h"
#include "bitarray/bit_array.h"

BIT_ARRAY* permutacao(BIT_ARRAY* entrada, int tabela[], int size) {
	BIT_ARRAY* permutado = bit_array_create(size);

	for (int i = 0; i < size; i++) {
		bit_array_assign_bit(permutado, i, bit_array_get_bit(entrada, tabela[i] - 1));
	}

	return permutado;
}

BIT_ARRAY* permutacaoIP(BIT_ARRAY* entrada) {
	int TABELA_IP[] = {2, 6, 3, 1, 4, 8, 5, 7};
	return permutacao(entrada, TABELA_IP, (int) (sizeof(TABELA_IP) / sizeof(int)));
}

BIT_ARRAY* permutacaoInversaIP(BIT_ARRAY* entrada) {
	int TABELA_IP_INVERSA[] = {4, 1, 3, 5, 7, 2, 8, 6};
	return permutacao(entrada, TABELA_IP_INVERSA, sizeof(TABELA_IP_INVERSA) / sizeof(int));
}

BIT_ARRAY* permutacaoP10(BIT_ARRAY* entrada) {
	int TABELA_P10[] = { 3, 5, 2, 7, 4, 10, 1, 9, 8, 6 };
	return permutacao(entrada, TABELA_P10, (int) (sizeof(TABELA_P10) / sizeof(int)));
}

BIT_ARRAY* permutacaoP8(BIT_ARRAY* entrada) {
	int TABELA_P8[] = { 6, 3, 7, 4, 8, 5, 10, 9 };
	return permutacao(entrada, TABELA_P8, (int) (sizeof(TABELA_P8) / sizeof(int)));
}

BIT_ARRAY* permutacaoP4(BIT_ARRAY* entrada) {
	int TABELA_P4[] = { 2, 4, 3, 1 };
	return permutacao(entrada, TABELA_P4, (int) (sizeof(TABELA_P4) / sizeof(int)));
}

void leftShift(BIT_ARRAY* entrada, int quantidade) {
	BIT_ARRAY* parte1 = bit_array_create(5);
	BIT_ARRAY* parte2 = bit_array_create(5);

	bit_array_copy(parte1, 0, entrada, 0, 5);
	bit_array_copy(parte2, 0, entrada, 5, 5);

	bit_array_cycle_right(parte1, quantidade);
	bit_array_cycle_right(parte2, quantidade);

	bit_array_copy(entrada, 0, parte1, 0, 5);
	bit_array_copy(entrada, 5, parte2, 0, 5);
}

BIT_ARRAY* criaSubChave1(BIT_ARRAY* chave) {
	BIT_ARRAY* subchaveIntermediaria = permutacaoP10(chave);
	leftShift(subchaveIntermediaria, 1);
	BIT_ARRAY* subchave = permutacaoP8(subchaveIntermediaria);

	char var[bit_array_length(subchave)];
	printf("subchave1=%s\n", bit_array_to_str(subchave, var));
	return subchave;
}

BIT_ARRAY* criaSubChave2(BIT_ARRAY* chave) {
	BIT_ARRAY* subchaveIntermediaria = permutacaoP10(chave);
	leftShift(subchaveIntermediaria, 1);
	leftShift(subchaveIntermediaria, 2);
	BIT_ARRAY* subchave = permutacaoP8(subchaveIntermediaria);

	char var[bit_array_length(subchave)];
	printf("subchave2=%s\n", bit_array_to_str(subchave, var));

	return subchave;
}

BIT_ARRAY* funcaoF(BIT_ARRAY* entrada, BIT_ARRAY* subchave) {
	char bits[8];

	int TABELA_EP[] = { 4, 1, 2, 3, 2, 3, 4, 1 };
	BIT_ARRAY* resultadoExpansao = permutacao(entrada, TABELA_EP, (int) (sizeof(TABELA_EP) / sizeof(int)));
	printf("resultadoEP=%s\n", bit_array_to_str(resultadoExpansao, bits));

	// XOR resultado da expansao com subchave1
	BIT_ARRAY* resultadoXOR = bit_array_create(bit_array_length(subchave));
	bit_array_xor(resultadoXOR, resultadoExpansao, subchave);
	printf("resultadoXOR1=%s\n", bit_array_to_str(resultadoXOR, bits));

	// separar entrada S0 e S1
	BIT_ARRAY* s0 = bit_array_create(4);
	BIT_ARRAY* s1 = bit_array_create(4);
	bit_array_copy(s0, 0, resultadoXOR, 0, 4);
	bit_array_copy(s1, 0, resultadoXOR, 4, 4);
	printf("S0=%s\n", bit_array_to_str(s0, bits));
	printf("S1=%s\n", bit_array_to_str(s1, bits));

	int MATRIZ_S0[4][4] = { { 1, 0, 3, 2 }, { 3, 2, 1, 0 }, { 0, 2, 1, 3 }, { 3,
			1, 3, 2 } };

	int MATRIZ_S1[4][4] = { { 0, 1, 2, 3 }, { 2, 0, 1, 3 }, { 3, 0, 1, 0 }, { 2,
			1, 0, 3 } };

	char linhaS0[2];
	char colunaS0[2];
	char linhaS1[2];
	char colunaS1[2];

	sprintf(linhaS0, "%d%d", bit_array_get_bit(s0, 0), bit_array_get_bit(s0, 3));
	sprintf(colunaS0, "%d%d", bit_array_get_bit(s0, 1), bit_array_get_bit(s0, 2));

	BIT_ARRAY* s0Linha = bit_array_create(2);
	bit_array_from_str(s0Linha, linhaS0);
	int s0LinhaDecimal = (int) strtol(linhaS0, NULL, 2);
	printf("s0Linha=%s\n", bit_array_to_str(s0Linha, bits));

	BIT_ARRAY* s0Coluna = bit_array_create(2);
	bit_array_from_str(s0Coluna, colunaS0);
	int s0ColunaDecimal = (int) strtol(colunaS0, NULL, 2);
	printf("s0Coluna=%s\n", bit_array_to_str(s0Coluna, bits));

	printf("s0LinhaDecimal=%d\n", s0LinhaDecimal);
	printf("s0ColunaDecimal=%d\n", s0ColunaDecimal);

	sprintf(linhaS1, "%d%d", bit_array_get_bit(s1, 0), bit_array_get_bit(s1, 3));
	sprintf(colunaS1, "%d%d", bit_array_get_bit(s1, 1), bit_array_get_bit(s1, 2));

	BIT_ARRAY* s1Linha = bit_array_create(2);
	bit_array_from_str(s1Linha, linhaS1);
	int s1LinhaDecimal = (int) strtol(linhaS1, NULL, 2);
	printf("s1Linha=%s\n", bit_array_to_str(s1Linha, bits));

	BIT_ARRAY* s1Coluna = bit_array_create(2);
	bit_array_from_str(s1Coluna, colunaS1);
	int s1ColunaDecimal = (int) strtol(colunaS1, NULL, 2);
	printf("s1Coluna=%s\n", bit_array_to_str(s1Coluna, bits));

	printf("s1LinhaDecimal=%d\n", s1LinhaDecimal);
	printf("s1ColunaDecimal=%d\n", s1ColunaDecimal);

	int s0Valor = MATRIZ_S0[s0LinhaDecimal][s0ColunaDecimal];
	int s1Valor = MATRIZ_S1[s1LinhaDecimal][s1ColunaDecimal];
	printf("s0ValorMatriz=%d\n", s0Valor);
	printf("s1ValorMatriz=%d\n", s1Valor);

	char* s0ValorChar[2];
	char* s1ValorChar[2];
	sprintf(s0ValorChar, "%d", s0Valor);
	sprintf(s1ValorChar, "%d", s1Valor);

	BIT_ARRAY* s0Resultado = bit_array_create(2);
	bit_array_from_decimal(s0Resultado, s0ValorChar);
	bit_array_reverse(s0Resultado);
	printf("s0=%s\n", bit_array_to_str(s0Resultado, bits));

	BIT_ARRAY* s1Resultado = bit_array_create(2);
	bit_array_from_decimal(s1Resultado, s1ValorChar);
	bit_array_reverse(s1Resultado);
	printf("s1=%s\n", bit_array_to_str(s1Resultado, bits));

	// Juntar resultados de S0 e S1
	BIT_ARRAY* sBoxResultado = bit_array_create(4);
	bit_array_copy(sBoxResultado, 0, s0Resultado, 0, 2);
	bit_array_copy(sBoxResultado, 2, s1Resultado, 0, 2);

	printf("s0+s1=%s\n", bit_array_to_str(sBoxResultado, bits));

	return permutacaoP4(sBoxResultado);
}

BIT_ARRAY* funcaofk(BIT_ARRAY* entradaE, BIT_ARRAY* entradaD, BIT_ARRAY* subchave) {
	BIT_ARRAY* resultadoFuncaoF = funcaoF(entradaD, subchave);
	char bits[bit_array_length(resultadoFuncaoF)];
	printf("funcaoF=%s\n", bit_array_to_str(resultadoFuncaoF, bits));

	BIT_ARRAY* resultadoXORfk = bit_array_create(bit_array_length(resultadoFuncaoF));
	bit_array_xor(resultadoXORfk, entradaE, resultadoFuncaoF);
	printf("XORfk=%s\n", bit_array_to_str(resultadoXORfk, bits));
	printf("entradaD=%s\n", bit_array_to_str(entradaD, bits));

	BIT_ARRAY* resultadofk = bit_array_create(bit_array_length(resultadoXORfk) + bit_array_length(entradaD));
	bit_array_copy(resultadofk, 0, resultadoXORfk, 0, bit_array_length(resultadoXORfk));
	bit_array_copy(resultadofk, bit_array_length(resultadoXORfk), entradaD, 0, bit_array_length(entradaD));
	return resultadofk;
}

BIT_ARRAY* iteracoes(BIT_ARRAY* entrada, BIT_ARRAY* subchave1, BIT_ARRAY* subchave2) {
	char bits[8];

	// separar metades esquerda e direita
	BIT_ARRAY* esquerda = bit_array_create(4);
	BIT_ARRAY* direita = bit_array_create(4);
	bit_array_copy(esquerda, 0, entrada, 0, 4);
	bit_array_copy(direita, 0, entrada, 4, 4);

	BIT_ARRAY* resultadofk1 = funcaofk(esquerda, direita, subchave1);
	printf("fk1=%s\n", bit_array_to_str(resultadofk1, bits));

	// switch
	bit_array_copy(esquerda, 0, resultadofk1, 0, 4);
	bit_array_copy(direita, 0, resultadofk1, 4, 4);

	BIT_ARRAY* resultadofk2 = funcaofk(direita, esquerda, subchave2);
	printf("fk2=%s\n", bit_array_to_str(resultadofk2, bits));
	return resultadofk2;
}

BIT_ARRAY* criptografar(BIT_ARRAY* mensagem, BIT_ARRAY* chave) {
	BIT_ARRAY* subchave1 = criaSubChave1(chave);
	BIT_ARRAY* subchave2 = criaSubChave2(chave);

	BIT_ARRAY* resultadoPermutacaoIP = permutacaoIP(mensagem);
	char var[bit_array_length(mensagem)];
	printf("permutacaoIP=%s\n", bit_array_to_str(resultadoPermutacaoIP, var));

	BIT_ARRAY* resultadoIteracoes = iteracoes(resultadoPermutacaoIP, subchave1, subchave2);
	BIT_ARRAY* resultadoPermutacaoInversaIP = permutacaoInversaIP(resultadoIteracoes);
	return resultadoPermutacaoInversaIP;
}

BIT_ARRAY* descriptografar(BIT_ARRAY* mensagem, BIT_ARRAY* chave) {
	BIT_ARRAY* subchave1 = criaSubChave1(chave);
	BIT_ARRAY* subchave2 = criaSubChave2(chave);

	BIT_ARRAY* resultadoPermutacaoIP = permutacaoIP(mensagem);
	BIT_ARRAY* resultadoIteracoes = iteracoes(resultadoPermutacaoIP, subchave2, subchave1);
	BIT_ARRAY* resultadoPermutacaoInversaIP = permutacaoInversaIP(resultadoIteracoes);
	return resultadoPermutacaoInversaIP;
}
