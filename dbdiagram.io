// ============================
// 1.  University / Faculty
// ============================

Table specialization {
  id               int [pk, increment]
  name             varchar(120) [unique, not null]
}

Table field {
  id               int [pk, increment]
  name             varchar(120) [unique, not null]
}

Table specialization_field {  // “srodne oblasti” koje čine uže usmerenje
  specialization_id int  [ref: > specialization.id]
  field_id          int  [ref: > field.id]
  Note: 'PK = specialization_id + field_id'
  indexes {
    (specialization_id, field_id) [pk]
  }
}

Table university {
  id               int [pk, increment]
  name             varchar(120) [unique, not null]
  specialization_id int [ref: > specialization.id]  // nullable (nije svaki univerzitet “primenjenih nauka”)
  indexes {
    (specialization_id)
  }
}

Table faculty {
  id               int [pk, increment]
  name             varchar(120) [not null]
  university_id    int [ref: > university.id]
  indexes {
    (name, university_id) [unique]  // svaki univerzitet ne sme imati duplikat naziva fakulteta
  }
}

Table faculty_field {            // oblasti koje fakultet izučava
  faculty_id       int [ref: > faculty.id]
  field_id         int [ref: > field.id]
  indexes {
    (faculty_id, field_id) [pk]
  }
}

// 2.  Trening centar i edukacija

Table training_center {
  id        int [pk, increment]
  name      varchar(120) [unique, not null]
  email     varchar(120)
  phone     varchar(32)
  street    varchar(120)
  number    varchar(16)
  city      varchar(120)
}

Enum degree_level {
  BACHELOR
  MASTER
  PHD
}

Table candidate {
  id               int [pk, increment]
  first_name       varchar(80) 
  last_name        varchar(80)
  jmbg             char(13) [unique, not null]
  birth_date       date
  residence        varchar(120)
  phone            varchar(32)
  email            varchar(120)
  faculty_id       int [ref: > faculty.id]
  degree_level     degree_level
  training_center_id int [ref: > training_center.id]
}

Table therapy_area {       // uže oblasti psihoterapije (CBT, Gestalt…)
  id   int [pk, increment]
  name varchar(120) [unique, not null]
}

Table therapist {
  id                 int [pk, increment]
  candidate_id       int [ref: > candidate.id]   // null dok kandidat ne položi
  first_name         varchar(80)
  last_name          varchar(80)
  jmbg               char(13) [unique, not null]
  birth_date         date
  residence          varchar(120)
  phone              varchar(32)
  email              varchar(120)
  certification_date date
  therapy_area_id    int [ref: > therapy_area.id]
  training_center_id int [ref: > training_center.id]
}

Table supervision {   // jedan supervizor može voditi više kandidata u različitim periodima
  id             int [pk, increment]
  supervisor_id  int [ref: > therapist.id]
  candidate_id   int [ref: > candidate.id]
  start_date     date
  end_date       date
  indexes {
    (candidate_id, start_date)  // brza pretraga aktivnog supervizora
  }
}

// 3.  Klijenti i seanse

Enum gender {
  M
  F
  O
}

Table client {
  id                int [pk, increment]
  first_name        varchar(80)
  last_name         varchar(80)
  birth_date        date
  gender            gender
  email             varchar(120)
  phone             varchar(32)
  prev_therapy      boolean
  problem_desc      text
}

Table currency {
  code char(3) [pk]          // ISO poput RSD, EUR, USD...
  name varchar(64)
}

Table currency_rate {        // istorija kurseva prema dinaru
  currency_code char(3) [ref: > currency.code]
  rate_date     date
  rsd_rate      decimal(12,4)
  indexes {
    (currency_code, rate_date) [pk]
  }
}

Table hourly_rate_history {  // prateći cenovnik po satu kroz vreme
  id            int [pk, increment]
  therapist_id  int [ref: > therapist.id]
  valid_from    date
  price_per_hour decimal(10,2)
  currency_code char(3) [ref: > currency.code]
  indexes {
    (therapist_id, valid_from) [unique]
  }
}

Table session {
  id              int [pk, increment]
  client_id       int [ref: > client.id]
  therapist_id    int [ref: > therapist.id, null]
  candidate_id    int [ref: > candidate.id, null]
  session_datetime datetime
  duration_min    int
  notes           text
  hourly_rate_id  int [ref: > hourly_rate_history.id]
  indexes {
    (client_id, session_datetime)
  }
}

// 4.  Psihološki testovi

Table psychological_test {
  id      int [pk, increment]
  area    varchar(120)
  name    varchar(120) [unique]
  price   decimal(10,2)
}

Table session_test {
  session_id int [ref: > session.id]
  test_id    int [ref: > psychological_test.id]
  result     varchar(120)
  indexes {
    (session_id, test_id) [pk]
  }
}

// 5.  Plaćanja i rate

Enum payment_method {
  CASH
  CARD
}

Table payment {
  id                 int [pk, increment]
  client_id          int [ref: > client.id]
  session_id         int [ref: > session.id]
  installment_no     int                // 1 ili 2
  purpose            varchar(120)
  currency_code      char(3) [ref: > currency.code]
  payment_method     payment_method
  amount             decimal(12,2)
  payment_timestamp  datetime
  indexes {
    (session_id, installment_no) [unique]
  }
}

// 6.  Evidencija objave podataka

Enum released_to {
  SUPERVISOR
  POLICE
  COURT
}

Table session_data_release {
  id           int [pk, increment]
  session_id   int [ref: > session.id]
  released_to  released_to
  release_date date
  reason       text
}
