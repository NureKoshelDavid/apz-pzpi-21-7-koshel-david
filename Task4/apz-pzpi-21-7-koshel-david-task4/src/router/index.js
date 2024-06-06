import Home from "../pages/Home"
import SignIn from "../pages/SignIn"
import Registration from "../pages/Registration"
import Saloons from "../pages/Saloons"
import SaloonForm from "../pages/SaloonForm"
import SaloonDetails from "../pages/SaloonDetails"
import StaffDetails from "../pages/StaffDetails"
import Staff from "../pages/Staff"
import Statistics from "../pages/Statistics"
import Settings from "../pages/Settings"
import Admin from "../pages/Admin"

export const privateRoutes = [
    {path: '/saloons', component: Saloons, exact: true},
    {path: '/saloons/add', component: SaloonForm, exact: true},
    {path: '/saloons/:id', component: SaloonDetails, exact: true},
    {path: '/users/:userId', component: StaffDetails, exact: true},
    {path: '/users/staff/:saloonId', component: Staff, exact: true},
    {path: '/statistics', component: Statistics, exact: true},
    {path: '/settings/:userId', component: Settings, exact: true}
]

export const publicRoutes = [
    {path: '/', component: Home, exact: false},
    {path: '/authorization', component: SignIn, exact: false},
    {path: '/register', component: Registration, exact: false}
]

export const adminRoutes = [
    {path: '/admin', component: Admin, exact: true},
]