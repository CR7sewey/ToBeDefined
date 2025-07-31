package com.example.tobedefined.common.modules

sealed class NavigationClasses(val id: Int, val route: String) {
    sealed class NavigationRoutes(val nid: Int, val nroute: String) : NavigationClasses(nid, nroute) {
        object Login : NavigationRoutes(nid = 0, nroute = "login")
        object Dashboard: NavigationRoutes(1,"dashboard")
        object ProductsList: NavigationRoutes(2, "products")
        object CreateItem: NavigationRoutes(3, "dashboard/createItem")
        object EditItem: NavigationRoutes(4, "dashboard/editItem")
        object EditItemSpecific: NavigationRoutes(5, "dashboard/editItem/{id}") {
            fun createRoute(id: Int) = "dashboard/editItem/$id"

        }
    }
}