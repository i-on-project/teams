import * as React from "react";
import { useNavigate } from "react-router-dom";
import { Button, Header } from "semantic-ui-react";

export type Sugestion = {
    message: string,
    href?: string
} 

/**
 * Function used to guide users in case of empty pages.
 */
export function NothingToShow({ children, sugestion }: { children: string, sugestion?: Sugestion }) {

    const navigate = useNavigate()

    return (
        <div>
            <Header as='h1' textAlign="center">{children}</Header>
            {sugestion &&
                <React.Fragment >
                    <Header as='h3' textAlign="center" color="grey" >{sugestion.message}</Header>
                    {sugestion.href && 
                        <Button fluid compact size="small" textAlign="center" onClick={() => navigate(sugestion.href)}>Click Here</Button>
                        }
                </React.Fragment>
            }
        </div>
    )
}