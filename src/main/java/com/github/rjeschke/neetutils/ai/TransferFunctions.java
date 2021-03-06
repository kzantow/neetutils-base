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
package com.github.rjeschke.neetutils.ai;

import java.io.IOException;

import com.github.rjeschke.neetutils.io.NInputStream;

/**
 *
 * @author René Jeschke (rene_jeschke@yahoo.de)
 *
 */
@Deprecated
public class TransferFunctions
{
    public static TransferFunction fromStream(final NInputStream in) throws IOException
    {
        final TransferFunctionType tf = TransferFunctionType.fromInt(in.readI32());
        switch (tf)
        {
        case UNITY:
            return new UnityTransferFunction();
        case LINEAR:
            return new LinearTransferFunction(in.readDouble());
        case SIGMOID:
            return new SigmoidTransferFunction();
        case TANH:
            return new TanhTransferFunction();
        case STEP:
            return new StepTransferFunction(in.readDouble());
        case ATAN:
            return new AtanTransferFunction();
        default:
            return null;
        }
    }
}
