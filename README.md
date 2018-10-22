# starter-demo

Demonstates usage of gramant starters (e.g. [auth-starter](https://github.com/gramant/auth-starter))

## Custom roles

Requires configuration of `RoleProvider` bean:

```
@Bean
public RoleProvider roleProvider() {
	return new RoleProvider.Default(
			new PrivilegedRole(new RoleId("ADMIN"), Arrays.asList(new PrivilegeId("EDIT_EMPLOYEES"), new PrivilegeId("EDIT_MANAGERS"))),
			new PrivilegedRole(new RoleId("MANAGER"), Collections.singletonList(new PrivilegeId("EDIT_EMPLOYEES"))),
			new PrivilegedRole(new RoleId("EMPLOYEE"), Collections.emptyList())
	);
}
```