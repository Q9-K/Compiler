/*
 * @Date: 2023-09-15 14:36:55
 * @Author: Q9K
 * @Description:
 */
#include "libsysy.h"

// 数组

void change(int pos, int array[], int val)
{
    array[pos] = val;
    return;
}
void change2(int pos1, int pos2, int matrix[][2], int val)
{
    matrix[pos1][pos2] = val;
    return;
}

int getzero()
{
    return 0;
}

int iszero(int x)
{
    if (!x)
        return 1;
    else
        return 0;
    return 1;
}

int main()
{
    printf("20374319\n");
    const int value1 = 0, _array1[2] = {0, 0}, matriX1[2][2] = {{0, 0}, {0, 0}};
    int value = 0, array[2] = {0, 0}, matrix[2][2] = {{0, 0}, {0, 0}};

    change(0, array, 20374319);
    change(0, matrix[0], 20374319);
    change2(0, 0, matrix, 20374319);
    for (int i = 0; i < 2; i = i + 1)
    {
        printf("%d ", array[i]);
    }
    printf("\n");
    for (int i = 0; i < 2; i = i + 1)
    {
        for (int j = 0; j < 2; j = j + 1)
        {
            printf("%d ", matrix[i][j]);
        }
        printf("\n");
    }
    int res = iszero(value);
    printf("%d\n", res);
    return 0;
}