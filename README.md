# REGRAS DO PROJETO
O projeto será separado em branchs, onde a main tem o estado atual e testado do projeto, e cada branch deve ser referente a uma implementação nova, ex:
 - Implementação de caso de uso
 - Nova versão de artefato
## USO DO CHANGELOG
Toda vez que se fizer um commit, adicionar no changelog.md o que foi feito de maneira sucinta com a data, exemplo: 
09-09 Added code of conduct

## TIPOS DE COMMIT E BRANCH
Existem três tipos de commit e/ou branch, sendo eles:
 - Feat: Implementação nova, que trás uma funcionalidade não existente no projeto
 - Fix: Consertos de codigo e correções de bug
 - Chore: Commits de coisas que não mudam a funcionalidade do projeto, por exemplo json ou yaml lock update, ou merge.

## MODELO DE BRANCH
Caso se faça necessario pode-se criar branchs para adicionar feats de maneira assincrona. Cada branch tendo o modelo 'tipo'/'task'/'resumo'

Por exemplo a tarefa de código P-5 do gantt de adição da tela de home teria como branch feat/P-5/home

## MODELO DE COMMIT
Os commits mantem um padrão parecido com os de branch, sendo o modelo 'tipo': 'resumo'

Por exemplo o commit de adicionado as API calls da home seria feat: home API calls

## MODELO DE PR
Quando se for mergear as branchs se deve criar os pull requests e esperar a aprovação dos SQA testarem para mergear na branch do projeto, esse PR deve seguir algumas regras:

 - Changelog documentado e coerente com as mudanças
 - Nome do PR: ['task'] 'resumo'. Exemplo: [P-5] added home screen
 - Resumo de tudo que foi adicionado naquela branch

## VERSIONAMENTO
O versionamento é local por branch e feito caso os devs saibam/ achem necessario, caso não se vá versionar por branch manter um track dos commits para fins de segurança. **NÃO MERGEAR NA MAIN ATÉ A REVISÃO DO CODIGO** somente quando aprovado, não excluir as branchs após merge
