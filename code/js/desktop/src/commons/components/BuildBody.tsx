import * as React from 'react';
import { ReactNode } from 'react';
import { Grid, GridColumn } from 'semantic-ui-react';
import { MenuItem, MenuVertical } from './Menu';
 
 export function BuildBody({menuItems, children}: {menuItems: MenuItem[], children: ReactNode}) {
    return (
        <Grid columns={2}>
        <GridColumn width={4}>
          <MenuVertical items={menuItems}/>
        </GridColumn>
        <GridColumn>
          {children}
        </GridColumn>
      </Grid>
    )
 }