#ifndef SDES_H_
#define SDES_H_

#include "bitarray/bit_array.h"

BIT_ARRAY* criptografar(BIT_ARRAY* mensagem, BIT_ARRAY* chave);
BIT_ARRAY* descriptografar(BIT_ARRAY* mensagem, BIT_ARRAY* chave);

#endif
