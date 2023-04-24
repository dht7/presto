/*
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

package com.facebook.presto.hudi;

import com.facebook.presto.testing.QueryRunner;
import org.intellij.lang.annotations.Language;
import org.testng.annotations.Test;

import java.util.Optional;

import static com.facebook.presto.hudi.HudiQueryRunner.createHudiQueryRunner;
import static java.lang.String.format;

public class TestHudiIntegration
        extends com.facebook.presto.hive.hudi.TestHudiIntegration
{
    @Override
    protected QueryRunner createQueryRunner()
            throws Exception
    {
        return createHudiQueryRunner(Optional.empty());
    }

    @Test
    public void testQueryWithPartitionFilter()
    {
        @Language("SQL") String sqlTemplate = "SELECT symbol, ts, dt FROM %s WHERE symbol = 'GOOG' AND dt >= '2018-08-30'";
        @Language("SQL") String sqlResult = "SELECT * FROM VALUES " +
            "('GOOG', '2018-08-31 09:59:00', '2018-08-31')," +
            "('GOOG', '2018-08-31 10:59:00', '2018-08-31')";
        assertQuery(format(sqlTemplate, "stock_ticks_cow"), sqlResult);
    }
}
