/**
 * Copyright (C) 2015 Dr. David H. Akehurst (http://dr.david.h.akehurst.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.akehurst.transform.binary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import net.akehurst.transform.binary.api.TransformException;

@RunWith(DataProviderRunner.class)
public class test_Transformer {

    @DataProvider
    public static Object[][] data_updateAllLeft2Right() {
        //@formatter:off
        return new Object[][] {
            { null, null, TransformException.class },
            { null, new Object[] {}, TransformException.class },
            { new Object[] {}, null, TransformException.class },
            { new Object[] {}, new Object[] {}, new Object[] {} },
            {
                new Object[] {},
                new Object[] { new BElement(1), new BElement(2), new BElement(3) },
                new Object[] {}
            },
            { new Object[] { new AElement(1), new AElement(2), new AElement(3) },
              new Object[] {},
              new Object[] { new BElement(1), new BElement(2), new BElement(3) }
            },
            { new Object[] { new AElement(1) },
              new Object[] { new BElement(1) },
              new Object[] { new BElement(1) }
            },
            { new Object[] { new AElement(1) },
              new Object[] { new BElement(2) },
              new Object[] { new BElement(1) }
            },
            { new Object[] { new AElement(2) },
              new Object[] { new BElement(1) },
              new Object[] { new BElement(2) }
            },
            { new Object[] { new AElement(1), new AElement(2) },
              new Object[] { new BElement(1) },
              new Object[] { new BElement(1), new BElement(2) }
            },
            { new Object[] { new AElement(1) },
              new Object[] { new BElement(1), new BElement(2) },
              new Object[] { new BElement(1) }
            },
            { new Object[] { new AElement(1), new AElement(2) },
              new Object[] { new BElement(2) },
              new Object[] { new BElement(1), new BElement(2) }
            },
            { new Object[] { new AElement(2) },
              new Object[] { new BElement(1), new BElement(2) },
              new Object[] { new BElement(2) }
            }
        };
        //@formatter:on
    }

    @Test
    @UseDataProvider("data_updateAllLeft2Right")
    public void test_updateAllLeft2Right(final Object[] leftArr, final Object[] rightArr, final Object result) {
        try {
            final List<AElement> leftList = null == leftArr ? null : new ArrayList(Arrays.asList(leftArr));
            final List<BElement> rightList = null == rightArr ? null : new ArrayList(Arrays.asList(rightArr));
            final ExampleTransformer transformer = new ExampleTransformer();
            transformer.updateAllLeft2Right(AElement2BElement.class, leftList, rightList);

            final List<BElement> resultList = null == result ? null : new ArrayList(Arrays.asList((Object[]) result));
            Assert.assertEquals(resultList, rightList);

        } catch (final Exception e) {
            if (e.getClass().equals(result)) {
                // ok
            } else {
                e.printStackTrace();
                Assert.fail(e.getMessage());
            }
        }
    }

    @DataProvider
    public static Object[][] data_updateAllRight2Left() {
        return new Object[][] {
            //
            { null, null, TransformException.class },
            //
            { null, new Object[] {}, TransformException.class },
            //
            { new Object[] {}, null, TransformException.class }, /**/ { new Object[] {}, new Object[] {}, /**/ new Object[] {} },
            //
            { new Object[] {}, /**/ new Object[] { new BElement(1), new BElement(2), new BElement(3) },
                /**/ new Object[] { new AElement(1), new AElement(2), new AElement(3) } },
            //
            { new Object[] { new AElement(1), new AElement(2), new AElement(3) }, new Object[] {}, new Object[] {} },
            //
            { new Object[] { new AElement(1) }, /**/ new Object[] { new BElement(1) }, /**/ new Object[] { new AElement(1) } },
            //
            { new Object[] { new AElement(1) }, /**/ new Object[] { new BElement(2) }, /**/ new Object[] { new AElement(2) } },
            //
            { new Object[] { new AElement(2) }, /**/ new Object[] { new BElement(1) }, /**/ new Object[] { new AElement(1) } },
            //
            { new Object[] { new AElement(1), new AElement(2) }, /**/ new Object[] { new BElement(1) }, /**/ new Object[] { new AElement(1) } },
            //
            { new Object[] { new AElement(1) }, /**/ new Object[] { new BElement(1), new BElement(2) },
                /**/ new Object[] { new AElement(1), new AElement(2) } },
            //
            { new Object[] { new AElement(1), new AElement(2) }, /**/ new Object[] { new BElement(2) }, /**/ new Object[] { new AElement(2) } },
            //
            { new Object[] { new AElement(2) }, /**/ new Object[] { new BElement(1), new BElement(2) }, /**/ new Object[] { new AElement(1), new AElement(2) } }
            //
        };
    }

    @Test
    @UseDataProvider("data_updateAllRight2Left")
    public void test_updateAllRight2Left(final Object[] leftArr, final Object[] rightArr, final Object result) {
        try {
            final List<AElement> leftList = null == leftArr ? null : new ArrayList(Arrays.asList(leftArr));
            final List<BElement> rightList = null == rightArr ? null : new ArrayList(Arrays.asList(rightArr));
            final ExampleTransformer transformer = new ExampleTransformer();
            transformer.updateAllRight2Left(AElement2BElement.class, leftList, rightList);

            final List<BElement> resultList = null == result ? null : new ArrayList(Arrays.asList((Object[]) result));
            Assert.assertEquals(resultList, leftList);

        } catch (final Exception e) {
            if (e.getClass().equals(result)) {
                // ok
            } else {
                e.printStackTrace();
                Assert.fail(e.getMessage());
            }
        }
    }
}
