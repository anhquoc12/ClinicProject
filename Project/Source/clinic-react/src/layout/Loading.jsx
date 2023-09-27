import { Spinner } from "react-bootstrap"

const Loading = () => {
    return (<Spinner
        as="span"
        animation="grow"
        size="sm"
        role="status"
        aria-hidden="true"
      />)
}

export default Loading