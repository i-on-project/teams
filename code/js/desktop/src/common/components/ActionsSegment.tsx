import * as React from "react";
import { Menu, Segment } from "semantic-ui-react";
import { Action } from "../types/siren";
import { FormInModal } from "./DefaultForm";

/**
 * Function represents the segment where the list of actions is displayed.
 */
export function ActionsSegment({ actions }: { actions: Action[] }) {
    return (
        <Segment color='blue'>
            <Menu text vertical>
                <Menu.Header as='h3'>Actions</Menu.Header>
                {
                    actions.map((action: Action) =>
                        <FormInModal action={action} key={action.name} >
                            {
                                <Menu.Item key={action.name} name={action.title} />
                            }
                        </FormInModal>
                    )
                }
            </Menu>
        </Segment>
    )
}