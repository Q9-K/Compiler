/*
 * @Date: 2023-09-17 16:02:24
 * @Author: Q9K
 * @Description:
 */
#include "libsysy.h"
// 补充情况
void noparams_print()
{
    printf("20374319\n");
    return;
}
void f()
{
}
int main()
{
    f();
    printf("20374319\n");
    const int a = 0;
    // printf("%d\n", a);
    int b = 0;
    for (;;)
    {
        if (b == 0)
        {
        }
        1;
        ;
        break;
    }
    for (; b < 0 || b > 0; b = b + 1)
    {
        break;
    }
    for (b = 0;; b = b + 1)
    {
        if (b == 0)
        {
            b = 1;
            continue;
        }
        else
        {
            break;
        }
    }

    for (b = 0; b < 0 && b > 0;)
    {
        break;
    }
    for (b = 0;;)
    {
        break;
    }
    for (; b < 0;)
    {
        break;
    }
    for (;; b = (b + 1) + (b - 1))
    {
        break;
    }
    for (;; b = (b + 1) - (b - 1))
    {
        break;
    }
    for (;; b = (b + 1) * (b - 1))
    {
        break;
    }
    for (;; b = (b + 1) / (b - 1))
    {
        break;
    }
    for (;; b = (b + 1) % (b - 1))
    {
        break;
    }
    for (;; b = b + 1)
    {
        break;
    }
    noparams_print();
    int temp = +1;
    temp = -+-1;
    temp = 0;
    for (b = temp;;)
    {
        break;
    }
    for (b = 1 * 2;;)
    {
        break;
    }
    for (b = 1 - 2;;)
    {
        break;
    }
    for (;; b = b * 2)
    {
        break;
    }
    for (;; b = b - 1)
    {
        break;
    }
    for (;; b = b % 1)
    {
        break;
    }
    for (b = 0; b < 0; b = b + 1)
        ;
    ;
    1;
    for (; 1 == 2;)
    {
    }
    for (int i = 0; i < 2; i = i + 1)
    {
        continue;
        i = i + 1;
    }
    for (int i = 0, j = 0; i < 0; i = i + 1, j = j + 1)
    {
        continue;
    }
    for (int i = 0; i < 1; i = i + 1)
    {
        b = getint();
    }
    for (int i = 0; i < 1; i = i + 1)
    {
        1;
    }
    if (1)
    {
        for (int i = 0; i < 1; i = i + 1)
        {
            continue;
        }
    }
    else
    {
        for (int i = 0; i < 1; i = i + 1)
        {
            continue;
        }
    }

    return 0;
}