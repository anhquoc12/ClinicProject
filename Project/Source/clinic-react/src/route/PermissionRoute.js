import cookie from 'react-cookies'
import { Navigate} from 'react-router-dom'
import Forbidden from '../error-pages/Forbidden'
import { Permission } from '../configs/Permission'

const PermissionRoute = ({ path, component }) => {
    let currentUser = cookie.load('user') || null

    if (currentUser === null)
        return <Forbidden />
    let role = currentUser.userRole
    if (!Permission[path].includes(role))
        return <Forbidden />
    else
        return component
}

export default PermissionRoute