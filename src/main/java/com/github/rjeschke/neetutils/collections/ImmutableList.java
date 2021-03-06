/*
 * Copyright (C) 2012 René Jeschke <rene_jeschke@yahoo.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.rjeschke.neetutils.collections;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

import com.github.rjeschke.neetutils.Objects;

/**
 *
 * @author René Jeschke (rene_jeschke@yahoo.de)
 *
 * @param <A>
 */
public class ImmutableList<A> implements List<A>, RandomAccess, Cloneable, Serializable
{
    private static final long serialVersionUID = 634340469636541150L;

    final Object[]            data;
    final int                 size;
    int                       hashCode         = 0;

    ImmutableList(final Object[] data, final int size)
    {
        this.data = data;
        this.size = size;
    }

    public ImmutableList(final Collection<? extends A> coll)
    {
        this.data = new Object[coll.size()];
        int i = 0;
        for (final A a : coll)
            this.data[i++] = a;
        this.size = this.data.length;
    }

    @SafeVarargs
    public ImmutableList(final A... coll)
    {
        this.data = Arrays.copyOf(coll, coll.length);
        this.size = this.data.length;
    }

    public ImmutableList(final Iterable<? extends A> coll)
    {
        Object[] d = new Object[8];
        int i = 0;
        for (final A a : coll)
        {
            if (i == d.length) d = Arrays.copyOf(d, (d.length * 3) >> 1);
            d[i++] = a;
        }
        this.data = Arrays.copyOf(d, i);
        this.size = i;
    }

    @Override
    public int size()
    {
        return this.size;
    }

    @Override
    public boolean isEmpty()
    {
        return this.size == 0;
    }

    @Override
    public boolean contains(final Object o)
    {
        if (o == null)
        {
            for (int i = 0; i < this.size; i++)
            {
                if (this.data[i] == null) return true;
            }
        }
        else
        {
            for (int i = 0; i < this.size; i++)
            {
                if (o.equals(this.data[i])) return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<A> iterator()
    {
        return new IIterator<>(this.data, 0, this.size);
    }

    @Override
    public Object[] toArray()
    {
        return Arrays.copyOf(this.data, this.data.length);
    }

    @Override
    public <T> T[] toArray(final T[] a)
    {
        if (a.length >= this.size)
        {
            System.arraycopy(this.data, 0, a, 0, this.size);
            return a;
        }

        return (T[])Arrays.copyOf(this.data, this.size, a.getClass());
    }

    @Override
    public boolean add(final A e)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(final Object o)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(final Collection<?> c)
    {
        for (final Object o : c)
        {
            if (!this.contains(o)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends A> c)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(final int index, final Collection<? extends A> c)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(final Collection<?> c)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(final Collection<?> c)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear()
    {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public A get(final int index)
    {
        if (index > this.size) throw new NoSuchElementException();
        return (A)this.data[index];
    }

    @Override
    public A set(final int index, final A element)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(final int index, final A element)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public A remove(final int index)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(final Object o)
    {
        for (int i = 0; i < this.size; i++)
        {
            if (Objects.equals(this.data[i], o)) return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(final Object o)
    {
        for (int i = this.size - 1; i >= 0; i--)
        {
            if (Objects.equals(this.data[i], o)) return i;
        }
        return -1;
    }

    @Override
    public ListIterator<A> listIterator()
    {
        return new ImmutableListIterator<>(this.data, 0, this.size);
    }

    @Override
    public ListIterator<A> listIterator(final int index)
    {
        return new ImmutableListIterator<>(this.data, index, this.size - index);
    }

    @Override
    public List<A> subList(final int fromIndex, final int toIndex)
    {
        return new SubList<>(this, fromIndex, toIndex);
    }

    @Override
    public int hashCode()
    {
        if (this.hashCode == 0 && this.size > 0)
        {
            int h = 0;
            for (int i = 0; i < this.size; i++)
                h = h * 31 + (this.data[i] != null ? this.data[i].hashCode() : 0);
            this.hashCode = h;
        }
        return this.hashCode;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == this) return true;

        if (!(obj instanceof ImmutableList)) return false;

        final ImmutableList<?> l = (ImmutableList<?>)obj;
        if (l.size != this.size) return false;

        for (int i = 0; i < this.size; i++)
        {
            if (!Objects.equals(this.data[i], l.data[i])) return false;
        }

        return true;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < this.data.length; i++)
        {
            if (i != 0) sb.append(", ");
            sb.append(this.data[i].toString());
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public ImmutableList<A> clone()
    {
        return this;
    }

    private final static class IIterator<A> implements Iterator<A>
    {
        private final Object[] data;
        private int            index;
        private final int      end;

        public IIterator(final Object[] data, final int index, final int size)
        {
            this.data = data;
            this.index = index;
            this.end = index + size;
        }

        @Override
        public boolean hasNext()
        {
            return this.index != this.end;
        }

        @SuppressWarnings("unchecked")
        @Override
        public A next()
        {
            if (this.index >= this.end) throw new NoSuchElementException();
            return (A)this.data[this.index++];
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }

    }

    private final static class ImmutableListIterator<A> implements ListIterator<A>
    {
        private final Object[] data;
        private final int      end;
        private final int      start;
        private int            index;

        public ImmutableListIterator(final Object[] data, final int index, final int size)
        {
            this.data = data;
            this.index = this.start = index;
            this.end = this.start + size;
        }

        @Override
        public boolean hasNext()
        {
            return this.index != this.end;
        }

        @SuppressWarnings("unchecked")
        @Override
        public A next()
        {
            if (this.index >= this.end) throw new NoSuchElementException();
            return (A)this.data[this.index++];
        }

        @Override
        public boolean hasPrevious()
        {
            return this.index > 0;
        }

        @SuppressWarnings("unchecked")
        @Override
        public A previous()
        {
            return (A)this.data[this.index - 1];
        }

        @Override
        public int nextIndex()
        {
            return this.index;
        }

        @Override
        public int previousIndex()
        {
            return this.index - 1;
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(final A e)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(final A e)
        {
            throw new UnsupportedOperationException();
        }
    }

    static class SubList<A> extends ImmutableList<A>
    {
        private static final long serialVersionUID = 4983360084210193434L;
        private final int         start;
        private final int         end;

        public SubList(final ImmutableList<A> list, final int start, final int end)
        {
            super(list.data, Math.max(0, end - start));
            this.start = Math.min(list.size, start);
            this.end = this.start + this.size;
        }

        @Override
        public boolean contains(final Object o)
        {
            if (o == null)
            {
                for (int i = this.size; i < this.end; i++)
                {
                    if (this.data[i] == null) return true;
                }
            }
            else
            {
                for (int i = this.start; i < this.end; i++)
                {
                    if (o.equals(this.data[i])) return true;
                }
            }
            return false;
        }

        @Override
        public Iterator<A> iterator()
        {
            return new IIterator<>(this.data, this.start, this.size);
        }

        @Override
        public Object[] toArray()
        {
            final Object[] ret = new Object[this.size];
            System.arraycopy(this.data, this.start, ret, 0, this.size);
            return ret;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> T[] toArray(final T[] a)
        {
            if (a.length >= this.size)
            {
                System.arraycopy(this.data, this.start, a, 0, this.size);
                return a;
            }

            return (T[])this.toArray();
        }

        @SuppressWarnings("unchecked")
        @Override
        public A get(final int index)
        {
            if (index > this.size) throw new NoSuchElementException();
            return (A)this.data[this.start + index];
        }

        @Override
        public int indexOf(final Object o)
        {
            for (int i = this.start; i < this.end; i++)
            {
                if (Objects.equals(this.data[i], o)) return i - this.start;
            }
            return -1;
        }

        @Override
        public int lastIndexOf(final Object o)
        {
            for (int i = this.end - 1; i >= this.start; i--)
            {
                if (Objects.equals(this.data[i], o)) return i - this.start;
            }
            return -1;
        }

        @Override
        public ListIterator<A> listIterator()
        {
            return new ImmutableListIterator<>(this.data, this.start, this.size);
        }

        @Override
        public ListIterator<A> listIterator(final int index)
        {
            return new ImmutableListIterator<>(this.data, this.start + index, this.size - index - this.start);
        }

        @Override
        public List<A> subList(final int fromIndex, final int toIndex)
        {
            return new SubList<>(this, this.start + fromIndex, this.start + toIndex);
        }

        @Override
        public int hashCode()
        {
            if (this.hashCode == 0 && this.size > 0)
            {
                int h = 0;
                for (int i = this.start; i < this.end; i++)
                    h = h * 31 + (this.data[i] != null ? this.data[i].hashCode() : 0);
                this.hashCode = h;
            }
            return this.hashCode;
        }

        @Override
        public boolean equals(final Object obj)
        {
            if (obj == this) return true;

            if (!(obj instanceof ImmutableList)) return false;

            final ImmutableList<?> l = (ImmutableList<?>)obj;
            if (l.size != this.size) return false;

            for (int i = this.start; i < this.end; i++)
            {
                if (!Objects.equals(this.data[i], l.data[i])) return false;
            }

            return true;
        }

        @Override
        public String toString()
        {
            final StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (int i = this.start; i < this.end; i++)
            {
                if (i != this.start) sb.append(", ");
                sb.append(this.data[i].toString());
            }
            sb.append(']');
            return sb.toString();
        }

        @Override
        public ImmutableList<A> clone()
        {
            return this;
        }
    }
}
