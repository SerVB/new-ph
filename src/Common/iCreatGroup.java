/*
 * MIT License
 *
 * Copyright (c) 2017
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package Common;

/**
 * Описание группы существ.
 */
public class iCreatGroup {

    /**
     * Тип существ.
     */
    private int  m_creatType;

    /**
     * Количество существ в группе.
     */
    private long m_count;

    /**
     *
     */
    final long[] CREAT_GROWTH_DIVIDER = {
	9, 9, 10, 10, 11, 12
    };

    /**
     * Конструктор.
     * @param ct тип существ.
     * @param count количество существ.
     */
    public iCreatGroup(int ct, long count) {
        this.m_creatType = ct;
        this.m_count = count;
    }

    /**
     * Конструктор без параметра. Вызывает iCreatGroup(ct, RANDOM_QUANTITY).
     * @param ct тип существ.
     */
    public iCreatGroup(int ct) {
        iCreatGroup(ct, RANDOM_QUANTITY);
    }

    /**
     * Конструктор без параметров.
     * Вызывает iCreatGroup(CREAT_UNKNOWN, RANDOM_QUANTITY);
     */
    public iCreatGroup() {
        iCreatGroup(cm_creatures.CREAT_UNKNOWN, RANDOM_QUANTITY);
    }

    /**
     * Заменить группу на новую.
     * @param ct тип существ.
     * @param count количество существ.
     */
    public void Reset(int ct, long count) {
        this.m_creatType = ct;
        this.m_count = count;
    }

    /**
     * Заменить группу на новую без параметра.
     * Вызывает Reset(ct, RANDOM_QUANTITY).
     * @param ct тип существ.
     */
    public void Reset(int ct) {
        Reset(ct, RANDOM_QUANTITY);
    }

    /**
     * Заменить группу на новую без параметров.
     */
    public void Reset() {
        Reset(cm_creatures.CREAT_UNKNOWN, RANDOM_QUANTITY);
    }

    /**
     * Существует ли группа?
     * @return true если существует, false иначе.
     */
    public boolean IsValid() {
        return m_creatType != cm_creatures.CREAT_UNKNOWN;
    }

    /**
     * Получить тип существ группы.
     * @return тип существ.
     */
    public int Type() {
        return m_creatType;
    }

    /**
     * Задать тип существ группы.
     * @param ct новый тип существ.
     */
    public void setType(int ct) {
        m_creatType = ct;
    }

    /**
     * Получить количество существ в группе.
     * @return количество существ.
     */
    public long Count() {
        return m_count;
    }

    /**
     * Задать количество существ в группе.
     * @param count
     */
    public void setCount(long count) {
        m_count = count;
    }

    /**
     * Добавить существ в группы.
     * @param count количество добавляемых к уже существующим.
     */
    public void addCount(long count) {
        m_count += count;
    }

    /**
     * Получить мощь группы.
     * @return мощь.
     */
    public long GroupPower() {
        return m_count*cm_creatures.CREAT_DESC[m_creatType].pidx;
    }

    /**
     *
     * @param weeks
     */
    public void Grow(long weeks) {
        tracer.check(m_count != RAND_VAL);

        while(weeks > 0) {
            long div = CREAT_GROWTH_DIVIDER[cm_creatures.CREAT_DESC[m_creatType].level - 1];
            if (m_count < cm_creatures.CREAT_DESC[m_creatType].growth*2) div /= 2;

            m_count = m_count + (m_count+(div-1)) / div;

            weeks--;
        }
    }

    /**
     *
     * Вызывает Grow(1)
     */
    public void Grow() {
        Grow(1);
    }
}