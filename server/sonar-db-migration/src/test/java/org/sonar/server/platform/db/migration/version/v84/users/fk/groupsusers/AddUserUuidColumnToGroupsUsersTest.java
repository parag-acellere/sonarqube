/*
 * SonarQube
 * Copyright (C) 2009-2021 SonarSource SA
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
package org.sonar.server.platform.db.migration.version.v84.users.fk.groupsusers;

import java.sql.SQLException;
import java.sql.Types;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.sonar.core.util.Uuids;
import org.sonar.db.CoreDbTester;
import org.sonar.server.platform.db.migration.step.DdlChange;

import static org.assertj.core.api.Assertions.assertThat;

public class AddUserUuidColumnToGroupsUsersTest {

  @Rule
  public CoreDbTester db = CoreDbTester.createForSchema(AddUserUuidColumnToGroupsUsersTest.class, "schema.sql");

  private DdlChange underTest = new AddUserUuidColumnToGroupsUsers(db.database());

  @Before
  public void setup() {
    insertGroupUser(Uuids.createFast(), 1L);
    insertGroupUser(Uuids.createFast(), 2L);
    insertGroupUser(Uuids.createFast(), 3L);
  }

  @Test
  public void add_uuid_column() throws SQLException {
    underTest.execute();

    db.assertColumnDefinition("groups_users", "user_uuid", Types.VARCHAR, 255, true);

    assertThat(db.countSql("select count(*) from groups_users"))
      .isEqualTo(3);
  }

  private void insertGroupUser(String uuid, Long userId) {
    db.executeInsert("groups_users",
      "group_uuid", uuid,
      "user_id", userId + 1);
  }
}
