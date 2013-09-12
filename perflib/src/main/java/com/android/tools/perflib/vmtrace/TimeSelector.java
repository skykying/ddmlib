/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.tools.perflib.vmtrace;

public abstract class TimeSelector {
    public abstract long get(MethodInfo info, String threadName);

    private static final TimeSelector sInclusiveThreadTimeSelector = new TimeSelector() {
        @Override
        public long get(MethodInfo info, String threadName) {
            return info.getInclusiveTime(threadName, ClockType.THREAD);
        }
    };

    private static final TimeSelector sInclusiveGlobalTimeSelector = new TimeSelector() {
        @Override
        public long get(MethodInfo info, String threadName) {
            return info.getInclusiveTime(threadName, ClockType.GLOBAL);
        }
    };

    private static final TimeSelector sExclusiveThreadTimeSelector = new TimeSelector() {
        @Override
        public long get(MethodInfo info, String threadName) {
            return info.getExclusiveTime(threadName, ClockType.THREAD);
        }
    };

    private static final TimeSelector sExclusiveGlobalTimeSelector = new TimeSelector() {
        @Override
        public long get(MethodInfo info, String threadName) {
            return info.getExclusiveTime(threadName, ClockType.GLOBAL);
        }
    };

    public static TimeSelector create(ClockType type, boolean useInclusiveTime) {
        switch (type) {
            case THREAD:
                return useInclusiveTime ?
                        sInclusiveThreadTimeSelector : sExclusiveThreadTimeSelector;
            case GLOBAL:
                return useInclusiveTime ?
                        sInclusiveGlobalTimeSelector : sExclusiveGlobalTimeSelector;
            default:
                throw new IllegalArgumentException();
        }
    }
}
