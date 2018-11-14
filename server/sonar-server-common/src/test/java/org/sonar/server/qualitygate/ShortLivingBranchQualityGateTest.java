/*
 * SonarQube
 * Copyright (C) 2009-2019 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.server.qualitygate;

import org.junit.Test;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.server.qualitygate.ShortLivingBranchQualityGate.Condition;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class ShortLivingBranchQualityGateTest {

  @Test
  public void defines_short_living_branches_hardcoded_quality_gate_conditions() {
    assertThat(ShortLivingBranchQualityGate.CONDITIONS)
      .extracting(Condition::getMetricKey, Condition::getOperator, Condition::getErrorThreshold)
      .containsExactly(
        tuple(CoreMetrics.OPEN_ISSUES_KEY, "GT", "0"),
        tuple(CoreMetrics.REOPENED_ISSUES_KEY, "GT", "0"));
  }

}