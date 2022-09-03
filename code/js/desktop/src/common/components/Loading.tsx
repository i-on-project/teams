import * as React from "react";
import { Container, Segment, Image, Divider, Loader } from "semantic-ui-react";

/**
 * Small component that represents a loading state
 */
export function Loading() {
    return (
        <Container>
            <Segment loading>
                <Image src='https://react.semantic-ui.com/images/wireframe/paragraph.png' />
            </Segment>
            <Divider section></Divider>
            <Loader active inline='centered' size='medium'></Loader>
        </Container>
    )
}