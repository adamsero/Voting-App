import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { VoterTableComponent } from './components/voter-table/voter-table.component';
import { CandidateTableComponent } from './components/candidate-table/candidate-table.component';
import { VoteSelectComponent } from './components/vote-select/vote-select.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
})
export class AppComponent implements OnInit {
  @ViewChild(VoterTableComponent, { static: false })
  voterTable: VoterTableComponent;

  @ViewChild(CandidateTableComponent, { static: false })
  candidateTable: CandidateTableComponent;

  @ViewChild(VoteSelectComponent, { static: false })
  voteSelect: VoteSelectComponent;

  constructor() {}

  ngOnInit(): void {}

  refreshTables(): void {
    this.voterTable.refreshVoters();
    this.candidateTable.refreshCandidates();
  }

  refreshVoterNames(): void {
    this.voteSelect.refreshVoters();
  }

  refreshCandidateNames(): void {
    this.voteSelect.refreshCandidates();
  }
}
