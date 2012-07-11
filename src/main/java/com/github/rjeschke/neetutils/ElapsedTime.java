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
package com.github.rjeschke.neetutils;

import java.util.ArrayList;

public class ElapsedTime
{
    private long min = Long.MAX_VALUE;
    private long max = Long.MIN_VALUE;
    private ArrayList<Long> runs = new ArrayList<Long>();
    
    public ElapsedTime()
    {
        //
    }
    
    public ElapsedTime reset()
    {
        this.min = Long.MAX_VALUE;
        this.max = Long.MIN_VALUE;
        this.runs.clear();
        return this;
    }
    
    public ElapsedTime measure(Runnable r)
    {
        return this.measure(r, 1);
    }
    
    public ElapsedTime measure(Runnable r, int runs)
    {
        for(int i = 0; i < runs; i++)
        {
            final long t0, t1, t;

            System.gc();
            
            t0 = System.nanoTime();
            r.run();
            t1 = System.nanoTime();
            
            t = t1 - t0;
            
            this.min = Math.min(this.min, t);
            this.max = Math.max(this.max, t);
            this.runs.add(t);
        }
        
        return this;
    }
    
    @Override
    public String toString()
    {
        long sum = 0;
        for(long l : this.runs)
            sum += l;
        
        return String.format("Min: %g, max: %g, avg: %g, tot: %g", this.min * 1e-9, this.max * 1e-9, sum * 1e-9 / this.runs.size(), sum * 1e-9);
    }
}