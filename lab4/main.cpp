/*
 * Програмне забезпечення високопродуктивних компʼютерних систем
 * Лабораторна робота №4
 * Варіант 17
 *
 * Завдання E = (B * MR) * (MM * MO) + min(B) * Q * d
 * ПВВ1 – E, MR, Q
 * ПВВ2 – B, MM
 * ПВВ3 – MO, d
 *
 * Пащенко Дмитро ІП-04
 * Дата 13.05.2023
 */

#include <iostream>
#include <omp.h>
#include <climits>

using namespace std;
const int N = 2000;
const int P = 4;
const int H = N / P;

int calculation1(int B[H], int id);

void calculation2(int ai);

void calculation3(int vector[N], int matrix[N][N], int result[N], int id);

void calculation4(int di, int ai, int S[N], int id);

int MR[N][N];
int Q[N];
int B[N];
int MM[N][N];
int MO[N][N];
int d;
int S[N];
int E[N];
int temp1[N][H];
int temp2[N];
int a = INT_MIN;

int main() {
    omp_lock_t CS2;
    omp_lock_t CS3;
    omp_init_lock(&CS2);
    omp_init_lock(&CS3);
#pragma omp parallel num_threads(P)
    {
        int id = omp_get_thread_num() + 1;
        printf("T%d is started\n", id);
        if (id == 1) {
            // Input MR
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    MR[i][j] = 1;
                }
            }

            // Input Q
            for (int i = 0; i < N; i++) {
                Q[i] = 1;
            }

        } else if (id == 2) {
            // Input B
            for (int i = 0; i < N; i++) {
                B[i] = 1;
            }

            // Input MM
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    MM[i][j] = 1;
                }
            }
        } else if (id == 3) {
            // Input MO
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    MO[i][j] = 1;
                }
            }

            // Input d
            d = 1;
        }

        // B1
#pragma omp barrier

        int a_i;
        // Calculation1 ai = min(Bh)
        a_i = calculation1(B, id);

        // Calculation2 a = min(a, ai)
#pragma omp critical(CS1)
        calculation2(a_i);

        // B2
#pragma omp barrier

        // Calculation3 Sh = B * MRh
        calculation3(B, MR, S, id);

        // B3
#pragma omp barrier

        int ai;
        int di;

        // Copy ai = a
        omp_set_lock(&CS2);
        ai = a;
        omp_unset_lock(&CS2);

        // Copy di = d
        omp_set_lock(&CS3);
        di = d;
        omp_unset_lock(&CS3);

        // Calculation4 Eh = S * (MM * MOh) + ai * Qh * di
        calculation4(di, ai, S, id);

        // B4
#pragma omp barrier

        if (id == 1) {
            cout << "E = ";
            for (int i : E)
                cout << i << ' ';
            cout << "\n";
        }

        printf("T%d is finished\n", id);
    }
}

// Calculation1 ai = min(Bh)
int calculation1(int B[H], int id) {
    int start = (id - 1) * H;
    int end = id * H;

    int min = B[start];

    for (int i = start; i < end; i++) {
        if (B[i] < min) {
            min = B[i];
        }
    }

    return min;
}

// Calculation2 a = min(a, ai)
void calculation2(int ai) {
    if (a == INT_MIN || ai < a) {
        a = ai;
    }
}

// Calculation3 Sh = B * MRh
void calculation3(int vector[N], int matrix[N][N], int result[N], int id) {
    int start = (id - 1) * H;
    int end = id * H;

    for (int i = start; i < end; i++) {
        for (int j = 0; j < N; j++) {
            result[i] += vector[j] * matrix[j][i];
        }
    }
}

// Calculation4 Eh = S * (MM * MOh) + ai * Qh * di
void calculation4(int di, int ai, int S[N], int id) {
    int start = (id - 1) * H;
    int end = id * H;

    // MM * MOh
    for (int i = 0; i < N; i++) {
        int g = 0;
        for (int j = start; j < end; j++) {
            temp1[i][g] = 0;
            for (int k = 0; k < N; k++) {
                temp1[i][g] += MM[i][k] * MO[k][j];
            }
            g++;
        }
    }

    // S * (MM * MOh)
    for (int i = start; i < end; i++) {
        for (int j = 0; j < N; j++) {
            temp2[i] += S[j] * temp1[j][i];
        }
    }

    // Eh = S * (MM * MOh) + ai * Qh * di
    for (int i = 0; i < N; i++) {
        E[i] = temp2[i] + ai * Q[i] * di;
    }
}
