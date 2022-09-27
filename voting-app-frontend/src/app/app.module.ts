import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { VoterTableComponent } from './components/voter-table/voter-table.component';
import { AgGridModule } from 'ag-grid-angular';
import { GridComponent } from './components/grid/grid.component';
import { CandidateTableComponent } from './components/candidate-table/candidate-table.component';
import { ToastrModule } from 'ngx-toastr';
import { VoteSelectComponent } from './components/vote-select/vote-select.component';

@NgModule({
  declarations: [
    AppComponent,
    VoterTableComponent,
    GridComponent,
    CandidateTableComponent,
    VoteSelectComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AgGridModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({ positionClass: 'toast-top-center' }),
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
