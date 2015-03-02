A Super Easy To Use Admin For Slick
===================================

What does this project do ?
---------------------------

> Create your admin page for slick tables very easily


Usage (work in progress)
------------------------

> First create and controller

```scala
package controllers

import slick.admin._

object Admin extends SlickAdmin {
   lazy val tables = TableQuery[UserTable] :: TableQuery[Item] :: Nil
}
```

> Second add routes

```scala
GET     /admin          controllers.Admin.index()
GET     /admin/*path    controllers.Admin.doGet(path: String)
POST    /admin/*path    controllers.Admin.doPost(path: String)
```
