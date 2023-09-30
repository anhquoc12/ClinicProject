import { USER_ROLE } from "./Enum"

const roles = (userRoles) => {
    if (userRoles === 'authenticated')
        return [USER_ROLE.ADMIN, USER_ROLE.DOCTOR, USER_ROLE.NURSE, USER_ROLE.OWNER, USER_ROLE.PATIENT]
    return userRoles
}

export const Permission = {
    '/appointment': roles([USER_ROLE.PATIENT]),
    '/list-appointment': roles([USER_ROLE.PATIENT]),
    '/nurse/confirmed': roles([USER_ROLE.NURSE]),
    '/nurse/present': roles([USER_ROLE.NURSE])
}