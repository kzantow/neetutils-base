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
package com.github.rjeschke.neetutils.vectors;

public class Vector2f
{
    public float x;
    public float y;

    public Vector2f()
    {
        // 0
    }

    public Vector2f(final float x, final float y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector2f(final float xy)
    {
        this.x = xy;
        this.y = xy;
    }

    public static Vector2f of(final float x, final float y)
    {
        return new Vector2f(x, y);
    }

    public Vector2f scale(final float f)
    {
        this.x *= f;
        this.y *= f;
        return this;
    }

    public Vector2f set(final float x, final float y)
    {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2f set(final Vector2f v)
    {
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    public Vector2f add(final Vector2f v)
    {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vector2f add(final Vector2f v, final float scale)
    {
        this.x += v.x * scale;
        this.y += v.y * scale;
        return this;
    }

    public Vector2f sub(final Vector2f v)
    {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    public Vector2f sub(final Vector2f v, final float scale)
    {
        this.x -= v.x * scale;
        this.y -= v.y * scale;
        return this;
    }

    public Vector2f mul(final Vector2f v)
    {
        this.x *= v.x;
        this.y *= v.y;
        return this;
    }

    public Vector2f mul(final Vector2f v, final float scale)
    {
        this.x *= v.x * scale;
        this.y *= v.y * scale;
        return this;
    }

    public Vector2f lerp(final Vector2f v, final float f)
    {
        this.x += (v.x - this.x) * f;
        this.y += (v.y - this.y) * f;
        return this;
    }

    public float dot(final Vector2f v)
    {
        return this.x * v.x + this.y * v.y;
    }

    public Vector2f normalize()
    {
        float len = this.x * this.x + this.y * this.y;
        if (len != 0)
        {
            len = 1.f / (float)Math.sqrt(len);
            this.x *= len;
            this.y *= len;
        }
        return this;
    }

    public Vector2f negate()
    {
        this.x = -this.x;
        this.y = -this.y;
        return this;
    }

    public float length()
    {
        return (float)Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public void intoArray(final float[] arr, final int offset)
    {
        arr[offset] = this.x;
        arr[offset + 1] = this.y;
    }

    @Override
    public Vector2f clone()
    {
        return new Vector2f(this.x, this.y);
    }

    @Override
    public String toString()
    {
        return "(" + this.x + ", " + this.y + ")";
    }
}
