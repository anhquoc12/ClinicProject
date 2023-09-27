import cookie from 'react-cookies'
import { Navigate } from 'react-router-dom'

const MyUserReducer = (currentstate, action) => {
    switch (action.type) {
        case "login":
            return action.payload
        case "logout":
            cookie.remove('token')
            cookie.remove('user')
            return  null
        default:
    }

    return currentstate
}

export default MyUserReducer